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
import io.piper.common.enums.CmdTypeEnum;
import io.piper.common.enums.MsgTypeEnum;
import io.piper.common.pojo.message.CmdMsgBody;
import io.piper.common.pojo.message.Msg;
import io.piper.common.task.AbstractMessageHandler;
import io.piper.common.util.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public void handler(Msg msg, Channel currentChannel) {
        if (MsgTypeEnum.CMD.type.equals(msg.getMsgType())) {
            this.cmdHandler(msg, currentChannel);
        } else if (ChatTypeEnum.SINGLE.type.equals(msg.getChatType())) {
            this.singleHandler(msg, currentChannel);
        } else if (ChatTypeEnum.CHATROOM.type.equals(msg.getChatType())) {
            this.chatRoomHandler(msg, currentChannel);
        } else if (ChatTypeEnum.GROUP.type.equals(msg.getChatType())) {
            this.groupHandler(msg, currentChannel);
        } else {
            throw new UnsupportedOperationException("type not found");
        }
    }

    private void cmdHandler(Msg msg, Channel currentChannel) {
        CmdMsgBody cmdMsgBody = msg.getCmdMsgBody();
        if (null == cmdMsgBody || null == cmdMsgBody.getType()) {
            return;
        }
        if (CmdTypeEnum.SUB_ROOM.type.equals(cmdMsgBody.getType())) {
            if (null != cmdMsgBody.getParams()) {
                String roomId = cmdMsgBody.getParams().get("roomId");
                if (StringUtil.isEmpty(roomId)) {
                    return;
                }
                Long lRoomId = Long.valueOf(roomId);
                if (null != currentChannel) {
                    WebSocketUser.putRoomChannel(lRoomId, currentChannel);
                    return;
                }
                Set<Channel> channels = WebSocketUser.get(msg.getFrom());
                if (null != channels) {
                    channels.forEach(ch -> WebSocketUser.putRoomChannel(lRoomId, ch));
                }
            }
        }
    }

    private void singleHandler(Msg msg, Channel currentChannel) {
        Set<Channel> channels = WebSocketUser.get(msg.getTo());
        if (channels != null) {
            TextWebSocketFrame frame = new TextWebSocketFrame(msg.toString());
            channels.forEach(ch -> ch.writeAndFlush(frame));
        }
    }

    private void chatRoomHandler(Msg msg, Channel currentChannel) {
        Set<Channel> channels = WebSocketUser.getRoomChannels(Long.valueOf(msg.getTo()));
        if (channels != null) {
            TextWebSocketFrame frame = new TextWebSocketFrame(msg.toString());
            channels.forEach(ch -> ch.writeAndFlush(frame));
        }
    }

    private void groupHandler(Msg msg, Channel currentChannel) {
        // todo
    }
}
