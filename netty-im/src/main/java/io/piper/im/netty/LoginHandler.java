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

import cn.hutool.json.JSONUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.piper.common.WebSocketUser;
import io.piper.common.constant.Constants;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.db.RedisDS;
import io.piper.common.util.StringUtil;

/**
 * LoginHandler
 *
 * @author piper
 */
@ChannelHandler.Sharable
public class LoginHandler extends ChannelInboundHandlerAdapter {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(LoginHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String uri = ((FullHttpRequest) msg).uri();
        if (!"/".equals(uri)) {
            String[] uriSplit = uri.split("/");
            String token = uriSplit[uriSplit.length - 1];
            if (StringUtil.isEmpty(token)) {
                throw IMException.build(IMErrorEnum.INVALID_TOKEN);
            }
            String tokenDTOStr = RedisDS.getJedis().get(Constants.USER_TOKEN + token);
            if (StringUtil.isEmpty(tokenDTOStr)) {
                throw IMException.build(IMErrorEnum.INVALID_TOKEN);
            }
            UserTokenDTO tokenDTO = JSONUtil.toBean(tokenDTOStr, UserTokenDTO.class);
            String uid = tokenDTO.getId().toString();
            WebSocketUser.put(uid, ctx.channel());
            ctx.channel().attr(AttributeKey.valueOf(Constants.USER_ATTRIBUTE_KEY)).set(tokenDTO);
            log.debug("用户：{} 上线", uid);
        }
        ctx.pipeline().remove(LoginHandler.class);
        super.channelRead(ctx, msg);
    }
}
