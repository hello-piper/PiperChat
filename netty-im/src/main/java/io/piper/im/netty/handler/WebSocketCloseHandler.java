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
package io.piper.im.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.piper.im.netty.ImUserHolder;

/**
 * WebSocketCloseHandler
 * @author piper
 */
@ChannelHandler.Sharable
public class WebSocketCloseHandler extends SimpleChannelInboundHandler<CloseWebSocketFrame> {
    private static final InternalLogger log = InternalLoggerFactory.getInstance(WebSocketCloseHandler.class);

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Long userKey = ImUserHolder.INSTANCE.getUserKey(ctx.channel());
        ImUserHolder.INSTANCE.removeSession(ctx.channel());
        log.info("用户下线 close {} {}", userKey, ImUserHolder.INSTANCE.onlineNum());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CloseWebSocketFrame msg) {
        Long userKey = ImUserHolder.INSTANCE.getUserKey(ctx.channel());
        log.info("receiveMsg close {}", userKey);
    }
}
