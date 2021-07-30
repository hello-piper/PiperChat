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

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.piper.common.WebSocketUser;
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.pojo.message.Msg;
import io.piper.common.task.AbstractMessageHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;

/**
 * MessageHandler
 *
 * @author piper
 * @date 2021-01-24 20:04
 */
public class MessageHandler extends AbstractMessageHandler {
    private static final Logger log = LogManager.getLogger(MessageHandler.class);
    public static final MessageHandler INSTANCE = new MessageHandler();

    @Override
    public void handler(Msg msg) {
        ChatTypeEnum chatTypeEnum = ChatTypeEnum.valueOf(msg.getChatType());
        if (Objects.isNull(chatTypeEnum)) {
            throw new UnsupportedOperationException("不支持的类型");
        }
        switch (chatTypeEnum) {
            case SINGLE:
                Channel channel = WebSocketUser.get(msg.getTo());
                if (channel != null) {
                    channel.writeAndFlush(new TextWebSocketFrame(msg.toString()));
                }
                break;
            case GROUP:
            case CHATROOM:
                List<Channel> channels = WebSocketUser.getGroupChannels(msg.getTo());
                if (channels != null) {
                    channels.forEach(v -> v.writeAndFlush(new TextWebSocketFrame(msg.toString())));
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + chatTypeEnum);
        }
    }
}
