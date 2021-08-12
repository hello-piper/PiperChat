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

import cn.hutool.json.JSONUtil;
import io.piper.common.WebSocketUser;
import io.piper.common.constant.Constants;
import io.piper.common.db.RedisDS;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.pojo.message.Msg;
import io.piper.common.util.StringUtil;
import io.piper.im.undertow.coder.JsonDecode;
import io.piper.im.undertow.coder.JsonEncode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/websocket/{token}", encoders = {JsonEncode.class}, decoders = {JsonDecode.class})
public class WebSocketEndpoint {
    private final Logger log = LogManager.getLogger(WebSocketEndpoint.class);

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        if (StringUtil.isEmpty(token)) {
            session.close();
            throw IMException.build(IMErrorEnum.INVALID_TOKEN);
        }
        String tokenDTOStr = RedisDS.getJedis().get(Constants.USER_TOKEN + token);
        if (StringUtil.isEmpty(tokenDTOStr)) {
            session.close();
            throw IMException.build(IMErrorEnum.INVALID_TOKEN);
        }
        UserTokenDTO tokenDTO = JSONUtil.toBean(tokenDTOStr, UserTokenDTO.class);
        String uid = tokenDTO.getId().toString();
        WebSocketUser.put(uid, session);
        session.setMaxIdleTimeout(30000);
        session.getUserProperties().put(Constants.USER_ATTRIBUTE_KEY, tokenDTO);
        log.debug("用户：{} 上线", uid);
    }

    @OnMessage
    public void message(Msg msg, Session session) {
        log.debug("来自客户端的消息 {}", msg);
        MessageHandler.INSTANCE.handler(msg, session);
    }

    @OnClose
    public void onClose(Session session) {
        UserTokenDTO tokenDTO = (UserTokenDTO) session.getUserProperties().get(Constants.USER_ATTRIBUTE_KEY);
        WebSocketUser.remove(tokenDTO.getId().toString());
        WebSocketUser.removeRoomChannel(session);
        log.debug("用户: {} 下线", tokenDTO.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("error {} {}", session.getId(), error.getMessage());
        session.getAsyncRemote().sendText(error.getMessage());
    }
}
