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

import io.piper.common.constant.ClientNameEnum;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.util.HashMultiMap;
import io.piper.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import java.util.*;

/**
 * UserSessionHolder
 *
 * @author piper
 */
public class UserSessionHolder {
    private static final Logger log = LoggerFactory.getLogger(UserSessionHolder.class);
    public static final String USER_KEY = "key";
    public static final String USER_INFO = "userInfo";

    // 用户的连接
    private static final HashMultiMap<String, Session> USER_SESSIONS = new HashMultiMap<>();
    // 在直播间内的用户连接
    private static final HashMultiMap<String, Session> ROOM_SESSIONS = new HashMultiMap<>();
    // 用户的连接在上边哪些key里面
    private static final HashMultiMap<Session, String> SESSION_LINK = new HashMultiMap<>();

    /**
     * 根据uid获取连接
     */
    public static Set<Session> getUserSession(String uid) {
        Set<Session> set = USER_SESSIONS.get(uid);
        if (set == null) {
            return Collections.emptySet();
        }
        return set;
    }

    /**
     * 根据uid获取直播间连接
     */
    public static Set<Session> getRoomSession(String roomId) {
        Set<Session> set = ROOM_SESSIONS.get(roomId);
        if (set == null) {
            return Collections.emptySet();
        }
        return set;
    }

    /**
     * 获取用户所有连接
     */
    public static Collection<Set<Session>> getUserAllSession() {
        return USER_SESSIONS.values();
    }

    /**
     * 添加用户连接
     */
    public static boolean putUserSession(String uid, Session session) {
        SESSION_LINK.put(session, uid);
        return USER_SESSIONS.put(uid, session);
    }

    /**
     * 连接数
     */
    public static int onlineNum() {
        return USER_SESSIONS.valueSize();
    }

    /**
     * 添加直播间连接
     */
    public static boolean putRoomSession(String roomId, Session session) {
        SESSION_LINK.put(session, roomId);
        ROOM_SESSIONS.put(roomId, session);
        return true;
    }

    /**
     * 移除直播间连接
     */
    public static boolean removeRoomSession(String roomId, Session session) {
        ROOM_SESSIONS.remove(roomId, session);
        SESSION_LINK.remove(session, roomId);
        return true;
    }

    /**
     * 移除连接
     */
    public static void removeSession(Session session) {
        if (session != null) {
            String uid = (String) session.getUserProperties().get(UserSessionHolder.USER_KEY);
            Set<String> keys = SESSION_LINK.get(session);
            if (keys == null) {
                keys = new HashSet<>();
            }
            if (uid != null) {
                keys.add(uid);
            }
            keys.forEach(key -> {
                USER_SESSIONS.remove(key, session);
                ROOM_SESSIONS.remove(key, session);
            });
            SESSION_LINK.removeKey(session);
        }
    }

    /**
     * 连接关闭
     */
    public static void close(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (Exception e) {
                log.info("sessionClose error {}", session.getUserProperties(), e);
            }
        }
    }

    /**
     * 根据连接获取uid
     */
    public static String getSessionUid(Session session) {
        if (session != null) {
            Object obj = session.getUserProperties().get(USER_KEY);
            if (obj != null) {
                return (String) obj;
            }
        }
        return null;
    }

    /**
     * 踢出相同端的连接
     */
    public static void kickOut(Session session, String key, UserTokenDTO dto) {
        String clientName = dto.getClientName();
        if (Objects.equals(clientName, ClientNameEnum.WEB.getName())) {
            Set<Session> userSession = getUserSession(key);
            long count = userSession.parallelStream().filter(s -> {
                UserTokenDTO tokenDTO = (UserTokenDTO) s.getUserProperties().get(USER_INFO);
                return tokenDTO != null && Objects.equals(tokenDTO.getClientName(), ClientNameEnum.WEB.getName());
            }).count();
            if (count > 10) {
                Optional<Session> first = userSession.parallelStream().filter(s -> {
                    UserTokenDTO tokenDTO = (UserTokenDTO) s.getUserProperties().get(USER_INFO);
                    return tokenDTO != null && Objects.equals(tokenDTO.getClientName(), ClientNameEnum.WEB.getName()) && session != s;
                }).findFirst();
                first.ifPresent(UserSessionHolder::close);
            }
        } else if (Objects.equals(clientName, ClientNameEnum.ANDROID.getName())) {
            getUserSession(key).parallelStream().iterator().forEachRemaining(s -> {
                UserTokenDTO tokenDTO = (UserTokenDTO) s.getUserProperties().get(USER_INFO);
                if (tokenDTO != null && s != session && StringUtil.equals(tokenDTO.getClientName(), ClientNameEnum.ANDROID.getName())) {
                    close(session);
                }
            });
        } else if (Objects.equals(clientName, ClientNameEnum.IOS.getName())) {
            getUserSession(key).parallelStream().iterator().forEachRemaining(s -> {
                UserTokenDTO tokenDTO = (UserTokenDTO) s.getUserProperties().get(USER_INFO);
                if (tokenDTO != null && s != session && StringUtil.equals(tokenDTO.getClientName(), ClientNameEnum.IOS.getName())) {
                    close(session);
                }
            });
        } else {
            close(session);
        }
    }

    public static String getUserKey(Session session) {
        return (String) session.getUserProperties().get(UserSessionHolder.USER_KEY);
    }

}
