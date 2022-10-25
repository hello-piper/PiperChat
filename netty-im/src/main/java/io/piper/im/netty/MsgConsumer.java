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
import io.netty.util.ReferenceCountUtil;
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.pojo.message.Msg;
import io.piper.common.spi.AbstractMsgConsumer;
import io.piper.common.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * MsgConsumer
 *
 * @author piper
 */
public class MsgConsumer extends AbstractMsgConsumer {
    private static final Logger log = LoggerFactory.getLogger(MsgConsumer.class);

    @Override
    public void handler(Msg msg) {
        if (msg == null || !msg.valid()) {
            return;
        }
        ThreadUtil.LISTENING_POOL.execute(() -> {
            if (ChatTypeEnum.SINGLE.type.equals(msg.getChatType())) {
                this.singleHandler(msg);
            } else if (ChatTypeEnum.CHATROOM.type.equals(msg.getChatType())) {
                this.chatRoomHandler(msg);
            }
        });
    }

    private void singleHandler(Msg msg) {
        Set<Channel> channels = ImUserHolder.INSTANCE.getUserSession(msg.getTo());
        if (channels.isEmpty()) {
            return;
        }
        TextWebSocketFrame frame = new TextWebSocketFrame(msg.toString());
        channels.forEach(ch -> ch.write(frame.retainedDuplicate()));
    }

    private void chatRoomHandler(Msg msg) {
        Set<Channel> channels = ImUserHolder.INSTANCE.getRoomSession(msg.getTo());
        if (channels.isEmpty()) {
            return;
        }

        TextWebSocketFrame frame = new TextWebSocketFrame(msg.toString());
        // 1 This is correct! But frame cannot be reused. So Memory grows over time .
        // At the beginning the memory increased from 500M to 1500M. which eventually led to OOM
        channels.forEach(ch -> ch.writeAndFlush(frame.retainedDuplicate()));

        // 2 This is not correct! Multiple channels only the first one can receive the frame?
        // Memory does not grows over time that meets my expectations.
        // Memory stable at 500M, the same as when I use Undertow
        // channels.forEach(ch -> ch.writeAndFlush(frame));

        ReferenceCountUtil.safeRelease(frame);

        // So!  Please give me a way to reuse frame. without memory dont growing over time.
    }

}
