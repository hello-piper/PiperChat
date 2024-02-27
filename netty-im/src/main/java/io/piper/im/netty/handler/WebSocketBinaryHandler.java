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

import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.piper.common.pojo.message.protoObj.Msg;
import io.piper.im.netty.ImUserHolder;

/**
 * WebSocketBinaryHandler
 * @author piper
 */
@ChannelHandler.Sharable
public class WebSocketBinaryHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
    private final InternalLogger log = InternalLoggerFactory.getInstance(WebSocketBinaryHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame frame) throws InvalidProtocolBufferException {
        Channel channel = ctx.channel();
        Long userKey = ImUserHolder.INSTANCE.getUserKey(channel);
        Msg msg = Msg.parseFrom(frame.content().nioBuffer());
        log.info("receiveMsg bin {} {}", msg, userKey);
        // todo echo msg process
        ByteBuf byteBuf = ctx.alloc().ioBuffer().writeBytes(msg.toByteArray());
        ctx.writeAndFlush(new BinaryWebSocketFrame(byteBuf));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Long userKey = ImUserHolder.INSTANCE.getUserKey(ctx.channel());
        log.error("{}", userKey, cause);
    }
}
