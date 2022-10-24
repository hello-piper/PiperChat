/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.im.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.piper.common.constant.ClientNameEnum;
import io.piper.common.constant.Constants;
import io.piper.common.db.RedisDS;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.config.ImProperties;
import io.piper.common.pojo.config.ServerProperties;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.util.StringUtil;
import io.piper.common.util.YamlUtil;

/**
 * LoginHandler
 *
 * @author piper
 */
@ChannelHandler.Sharable
public class WebSocketLoginHandler extends ChannelInboundHandlerAdapter {
    private final InternalLogger log = InternalLoggerFactory.getInstance(WebSocketLoginHandler.class);
    private final ServerProperties config = YamlUtil.getConfig("server", ServerProperties.class);
    private final ImProperties imConfig = YamlUtil.getConfig("im", ImProperties.class);
    private static volatile long guest = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String uri = ((FullHttpRequest) msg).uri();
        if (uri.startsWith(config.getWsPath())) {
            String[] uriSplit = uri.split("/");
            String token = uriSplit[uriSplit.length - 1];
            if (StringUtil.isEmpty(token)) {
                throw IMException.build(IMErrorEnum.INVALID_TOKEN);
            }
            UserTokenDTO tokenDTO;
            if ("guest".equals(token)) {
                tokenDTO = new UserTokenDTO();
                tokenDTO.setId(-++guest);
                tokenDTO.setNickname("guest:" + tokenDTO.getId());
                tokenDTO.setClientName(ClientNameEnum.WEB.getName());
            } else {
                String tokenDTOStr = RedisDS.getJedis().get(Constants.USER_TOKEN + token);
                if (StringUtil.isEmpty(tokenDTOStr)) {
                    ImUserHolder.INSTANCE.close(ctx.channel());
                    throw IMException.build(IMErrorEnum.INVALID_TOKEN);
                }
                tokenDTO = JSON.parseObject(tokenDTOStr, UserTokenDTO.class);
            }
            Long userKey;
            if (tokenDTO.getId() != null) {
                userKey = tokenDTO.getId();
            } else {
                userKey = -(long) tokenDTO.getDeviceNo().hashCode();
            }
            boolean isOk = ImUserHolder.INSTANCE.putUserSession(userKey, ctx.channel());
            if (!isOk) {
                ImUserHolder.INSTANCE.close(ctx.channel());
                return;
            }
            tokenDTO.setTimestamp(System.currentTimeMillis());
            ctx.channel().attr(AttributeKey.valueOf(ImUserHolder.USER_KEY)).set(userKey);
            ctx.channel().attr(AttributeKey.valueOf(ImUserHolder.USER_INFO)).set(tokenDTO);
            ImUserHolder.INSTANCE.putRoomSession(imConfig.getSystemRoom(), ctx.channel());
            ImUserHolder.INSTANCE.kickOut(ctx.channel(), userKey, tokenDTO);
            log.info("用户上线 {} {} {} {}", userKey, token, tokenDTO, ImUserHolder.INSTANCE.onlineNum());
            ctx.pipeline().remove(WebSocketLoginHandler.class);
            ctx.fireChannelRead(msg);
        }
    }

}
