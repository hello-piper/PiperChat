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

import cn.hutool.json.JSONUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.piper.common.WebSocketUser;
import io.piper.common.pojo.message.Msg;

/**
 * WebSocketFrameHandler
 *
 * @author piper
 */
@ChannelHandler.Sharable
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    protected static final InternalLogger log = InternalLoggerFactory.getInstance(WebSocketFrameHandler.class);

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        // todo 清理用户在线状态
        WebSocketUser.remove(ctx.channel().id().asShortText());
        log.debug("用户: {} 下线", ctx.channel().id().asShortText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) {
        // ping and pong frames already handled
        if (frame != null) {
            // Send the uppercase string back.
            MessageHandler.send(JSONUtil.toBean(frame.text(), Msg.class));
        } else {
            throw new UnsupportedOperationException("frame is null");
        }
    }
}
