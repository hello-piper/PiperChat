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

import io.netty.channel.Channel;
import io.piper.common.WebSocketUser;
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.pojo.message.Msg;

import java.util.List;
import java.util.Objects;

/**
 * MessageHandler
 *
 * @author piper
 * @date 2021-01-24 20:04
 */
public class MessageHandler {

    public static void send(Msg dto) {
        ChatTypeEnum chatTypeEnum = ChatTypeEnum.valueOf(dto.getChatType());
        if (Objects.isNull(chatTypeEnum)) {
            throw new UnsupportedOperationException("不支持的类型");
        }

        switch (chatTypeEnum) {
            case SINGLE:
                Channel channel = WebSocketUser.get(dto.getTo());
                if (channel != null) {
                    channel.writeAndFlush(dto.toString());
                }
                break;
            case GROUP:
            case CHATROOM:
                List<Channel> channels = WebSocketUser.getGroupChannels(dto.getTo());
                if (channels != null) {
                    channels.forEach(v -> v.writeAndFlush(dto.toString()));
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + chatTypeEnum);
        }
    }
}
