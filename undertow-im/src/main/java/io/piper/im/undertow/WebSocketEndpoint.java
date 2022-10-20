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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.piper.common.constant.ClientNameEnum;
import io.piper.common.constant.Constants;
import io.piper.common.db.RedisDS;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.config.ImProperties;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.pojo.req.RequestMsg;
import io.piper.common.util.StringUtil;
import io.piper.common.util.YamlUtil;
import io.piper.im.undertow.coder.JsonDecode;
import io.piper.im.undertow.coder.JsonEncode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

@ServerEndpoint(value = "/websocket/{token}", encoders = {JsonEncode.class}, decoders = {JsonDecode.class})
public class WebSocketEndpoint {
    private final Logger log = LoggerFactory.getLogger(WebSocketEndpoint.class);
    private final ImProperties config = YamlUtil.getConfig("im", ImProperties.class);
    private static volatile long guest = 0;

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        if (StringUtil.isEmpty(token)) {
            session.close();
            throw IMException.build(IMErrorEnum.INVALID_TOKEN);
        }
        UserTokenDTO tokenDTO;
        if ("guest".equals(token)) {
            tokenDTO = new UserTokenDTO();
            tokenDTO.setId(-++guest);
            tokenDTO.setNickname("guest:" + tokenDTO.getId());
            tokenDTO.setClientName(ClientNameEnum.WEB.getName());
        } else {
            String tokenDTOStr = RedisDS.getJedis().get(Constants.USER_TOKEN + token);
            if (StringUtil.isEmpty(tokenDTOStr)) {
                UserSessionHolder.close(session);
                throw IMException.build(IMErrorEnum.INVALID_TOKEN);
            }
            tokenDTO = JSON.parseObject(tokenDTOStr, UserTokenDTO.class);
        }
        String userKey;
        if (tokenDTO.getId() != null) {
            userKey = tokenDTO.getId().toString();
        } else {
            userKey = tokenDTO.getDeviceNo();
        }
        boolean isOk = UserSessionHolder.putUserSession(userKey, session);
        if (!isOk) {
            UserSessionHolder.close(session);
            return;
        }
        tokenDTO.setTimestamp(System.currentTimeMillis());
        session.setMaxIdleTimeout(180000);
        session.getUserProperties().put(UserSessionHolder.USER_KEY, userKey);
        session.getUserProperties().put(UserSessionHolder.USER_INFO, tokenDTO);
        UserSessionHolder.putRoomSession(config.getSystemRoom(), session);
        UserSessionHolder.kickOut(session, userKey, tokenDTO);
        log.info("用户上线 {} {} {} {}", userKey, token, tokenDTO, UserSessionHolder.onlineNum());
    }

    @OnMessage
    public void message(String msg, Session session) {
        String userKey = (String) session.getUserProperties().get(UserSessionHolder.USER_KEY);
        log.info("receiveMsg {} {}", msg, userKey);
        if (StringUtil.isEmpty(msg)) {
            UserSessionHolder.close(session);
            return;
        }
        if ("ping".equals(msg)) {
            session.getAsyncRemote().sendText("pong");
            return;
        }
        try {
            RequestMsg requestMsg = JSONObject.parseObject(msg, RequestMsg.class);
            if (requestMsg.getType() == null || requestMsg.getData() == null || requestMsg.getData().isEmpty()) {
                UserSessionHolder.close(session);
                return;
            }
            RequestMsg.RequestTypeEnum requestTypeEnum = RequestMsg.RequestTypeEnum.valueOf(requestMsg.getType());
            if (requestTypeEnum == null) {
                UserSessionHolder.close(session);
                return;
            }
            if (RequestMsg.RequestTypeEnum.ENTER_ROOM == requestTypeEnum) {
                // 进入直播间
                Map<String, Object> data = requestMsg.getData();
                String roomId = String.valueOf(data.get("roomId"));
                UserSessionHolder.putRoomSession(roomId, session);
            } else if (RequestMsg.RequestTypeEnum.EXIT_ROOM == requestTypeEnum) {
                // 退出直播间
                Map<String, Object> data = requestMsg.getData();
                String roomId = String.valueOf(data.get("roomId"));
                UserSessionHolder.removeRoomSession(roomId, session);
            }
        } catch (Exception e) {
            log.error("receiveMsg {} {}", msg, userKey, e);
        }
    }

    @OnClose
    public void onClose(Session session) {
        String userKey = (String) session.getUserProperties().get(UserSessionHolder.USER_KEY);
        UserSessionHolder.removeSession(session);
        if (userKey != null) {
            log.info("用户下线 {} {}", userKey, UserSessionHolder.onlineNum());
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("{}", session.getUserProperties().get(UserSessionHolder.USER_KEY), error);
    }

}
