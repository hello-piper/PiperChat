/*
 * Copyright 2020 The PiperChat
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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.AttributeKey;
import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.piper.common.WebSocketUser;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;

/**
 * LoginHandler
 *
 * @author piper
 */
public class LoginHandler extends ChannelInboundHandlerAdapter {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(LoginHandler.class);
    public static final AttributeKey<String> TOKEN_ATTRIBUTE_KEY = AttributeKey.valueOf("token");

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String uri = ((FullHttpRequest) msg).uri();
        if (!"/".equals(uri)) {
            String[] uriSplit = uri.split("/");
            String token = uriSplit[uriSplit.length - 1];
            if (StringUtil.isNullOrEmpty(token)) {
                throw IMException.build(IMErrorEnum.INVALID_TOKEN, "请登录");
            }
            ctx.channel().attr(TOKEN_ATTRIBUTE_KEY).set(token);
            // todo 解析用户token获取用户uid  放入用户容器
            WebSocketUser.put(ctx.channel().id().asShortText(), ctx.channel());
            log.debug("用户：{} 上线", ctx.channel().id().asShortText());
        }
        ctx.pipeline().remove(LoginHandler.class);
        super.channelRead(ctx, msg);
    }
}