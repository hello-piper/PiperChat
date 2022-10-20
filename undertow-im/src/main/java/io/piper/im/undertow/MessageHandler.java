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
package io.piper.im.undertow;

import io.piper.common.WebSocketUser;
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.pojo.message.Msg;
import io.piper.common.task.AbstractMessageHandler;
import io.piper.common.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.Set;

/**
 * MessageHandler
 *
 * @author piper
 */
public class MessageHandler extends AbstractMessageHandler {
    private static final Logger log = LoggerFactory.getLogger(MessageHandler.class);
    public static final MessageHandler INSTANCE = new MessageHandler();

    @Override
    public void handler(Msg msg) {
        this.handler(msg, null);
    }

    /**
     * msg process
     */
    public void handler(Msg msg, Session currentSession) {
        ThreadUtil.LISTENING_POOL.execute(() -> {
            if (ChatTypeEnum.SINGLE.type.equals(msg.getChatType())) {
                this.singleHandler(msg, currentSession);
            } else if (ChatTypeEnum.CHATROOM.type.equals(msg.getChatType())) {
                this.chatRoomHandler(msg, currentSession);
            } else {
                throw new UnsupportedOperationException("type not found");
            }
        });
    }

    private void singleHandler(Msg msg, Session currentSession) {
        Set<Session> sessions = WebSocketUser.get(msg.getTo());
        if (sessions != null) {
            sessions.forEach(s -> s.getAsyncRemote().sendObject(msg));
        }
    }

    private void chatRoomHandler(Msg msg, Session currentSession) {
        Set<Session> sessions = WebSocketUser.getRoomChannels(msg.getTo());
        if (sessions != null) {
            sessions.forEach(s -> s.getAsyncRemote().sendObject(msg));
        }
    }


}
