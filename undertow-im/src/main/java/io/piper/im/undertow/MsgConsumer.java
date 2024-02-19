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

import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.pojo.message.Msg;
import io.piper.common.spi.AbstractMsgConsumer;
import io.piper.common.util.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
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
            } else if (ChatTypeEnum.GROUP.type.equals(msg.getChatType())) {
                this.chatRoomHandler(msg);
            }
        });
    }

    private void singleHandler(Msg msg) {
        for (Long uid : msg.getTo()) {
            Set<Session> sessions = ImUserHolder.INSTANCE.getUserSession(uid);
            sessions.forEach(s -> s.getAsyncRemote().sendObject(msg));
        }
    }

    private void chatRoomHandler(Msg msg) {
        Set<Session> sessions = ImUserHolder.INSTANCE.getRoomSession(msg.getChatId());
        sessions.forEach(s -> s.getAsyncRemote().sendObject(msg));
    }

}
