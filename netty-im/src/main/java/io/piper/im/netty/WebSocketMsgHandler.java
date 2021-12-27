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

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.piper.common.pojo.message.Msg;

/**
 * WebSocketFrameHandler
 *
 * @author piper
 */
@ChannelHandler.Sharable
public class WebSocketMsgHandler extends SimpleChannelInboundHandler<Msg> {
    protected static final InternalLogger log = InternalLoggerFactory.getInstance(WebSocketMsgHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) {
        if (msg != null) {
            MessageHandler.INSTANCE.handler(msg, ctx.channel());
        } else {
            throw new UnsupportedOperationException("msg is null");
        }
    }
}
