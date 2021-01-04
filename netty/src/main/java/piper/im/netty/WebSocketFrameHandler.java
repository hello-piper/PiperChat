/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package piper.im.netty;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import piper.im.common.MessageDTO;
import piper.im.common.MessageOpeEnum;
import piper.im.common.MessageTypeEnum;

import java.util.List;
import java.util.Objects;

/**
 * Echoes uppercase content of text frames.
 *
 * @author piper
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    protected static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketFrameHandler.class);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        logger.debug("用户：" + ctx.channel().id().asLongText() + "上线");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        logger.debug("用户下线: " + ctx.channel().id().asLongText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame) throws Exception {
        // ping and pong frames already handled

        if (frame != null) {
            // Send the uppercase string back.
            handlerMessageDTO(JSON.parseObject(frame.text(), MessageDTO.class));
        } else {
            throw new UnsupportedOperationException("frame is null");
        }
    }

    /**
     * 处理消息
     *
     * @param dto
     */
    private void handlerMessageDTO(MessageDTO dto) {
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.valueOf(dto.getType());
        if (Objects.isNull(messageTypeEnum)) {
            throw new UnsupportedOperationException("不支持的类型");
        }

        MessageOpeEnum messageOpeEnum = MessageOpeEnum.valueOf(dto.getOpe());
        if (Objects.isNull(messageOpeEnum)) {
            throw new UnsupportedOperationException("不支持的类型");
        }

        switch (messageOpeEnum) {
            case peerToPeer:
                Channel channel = WebSocketUser.get(dto.getTo());
                if (channel != null) {
                    channel.writeAndFlush(new TextWebSocketFrame(dto.getBody()));
                }
                break;
            case group:
                List<Channel> channels = WebSocketUser.getGroupChannels(dto.getTo());
                if (channels != null) {
                    channels.forEach(v -> v.writeAndFlush(new TextWebSocketFrame(dto.getBody())));
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + messageOpeEnum);
        }
    }

}
