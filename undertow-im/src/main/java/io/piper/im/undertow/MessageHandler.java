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
import io.piper.common.enums.CmdTypeEnum;
import io.piper.common.enums.MsgTypeEnum;
import io.piper.common.pojo.message.CmdMsgBody;
import io.piper.common.pojo.message.Msg;
import io.piper.common.task.AbstractMessageHandler;
import io.piper.common.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.Session;
import java.util.Set;

/**
 * MessageHandler
 *
 * @author piper
 */
public class MessageHandler extends AbstractMessageHandler {
    private static final Logger log = LogManager.getLogger(MessageHandler.class);
    public static final MessageHandler INSTANCE = new MessageHandler();

    @Override
    public void handler(Msg msg) {
        this.handler(msg, null);
    }

    public void handler(Msg msg, Session currentSession) {
        if (MsgTypeEnum.CMD.type.equals(msg.getMsgType())) {
            this.cmdHandler(msg, currentSession);
        } else if (ChatTypeEnum.SINGLE.type.equals(msg.getChatType())) {
            this.singleHandler(msg, currentSession);
        } else if (ChatTypeEnum.CHATROOM.type.equals(msg.getChatType())) {
            this.chatRoomHandler(msg, currentSession);
        } else if (ChatTypeEnum.GROUP.type.equals(msg.getChatType())) {
            this.groupHandler(msg, currentSession);
        } else {
            throw new UnsupportedOperationException("Type not found");
        }
    }

    private void cmdHandler(Msg msg, Session currentSession) {
        CmdMsgBody cmdMsgBody = msg.getCmdMsgBody();
        if (null == cmdMsgBody || null == cmdMsgBody.getType()) {
            return;
        }
        if (CmdTypeEnum.SUB_ROOM.type.equals(cmdMsgBody.getType())) {
            if (null != cmdMsgBody.getParams()) {
                String roomId = cmdMsgBody.getParams().get("roomId");
                if (StringUtil.isNotEmpty(roomId)) {
                    WebSocketUser.putRoomChannel(Long.valueOf(roomId), currentSession);
                }
            }
        }
    }

    private void singleHandler(Msg msg, Session currentSession) {
        Session channel = WebSocketUser.get(msg.getTo());
        if (channel != null) {
            channel.getAsyncRemote().sendObject(msg);
        }
    }

    private void chatRoomHandler(Msg msg, Session currentSession) {
        Set<Session> sessions = WebSocketUser.getRoomChannels(Long.valueOf(msg.getTo()));
        if (sessions != null) {
            sessions.forEach(v -> v.getAsyncRemote().sendObject(msg));
        }
    }

    private void groupHandler(Msg msg, Session currentSession) {
        // todo
    }
}
