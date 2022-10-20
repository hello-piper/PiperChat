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
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.piper.common.constant.ClientNameEnum;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.util.HashMultiMap;
import io.piper.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final HashMultiMap<String, Channel> USER_SESSIONS = new HashMultiMap<>();
    // 在直播间内的用户连接
    private static final HashMultiMap<String, Channel> ROOM_SESSIONS = new HashMultiMap<>();
    // 用户的连接在上边哪些key里面
    private static final HashMultiMap<Channel, String> SESSION_LINK = new HashMultiMap<>();

    /**
     * 根据uid获取连接
     */
    public static Set<Channel> getUserSession(String uid) {
        Set<Channel> set = USER_SESSIONS.get(uid);
        if (set == null) {
            return Collections.emptySet();
        }
        return set;
    }

    /**
     * 根据uid获取直播间连接
     */
    public static Set<Channel> getRoomSession(String roomId) {
        Set<Channel> set = ROOM_SESSIONS.get(roomId);
        if (set == null) {
            return Collections.emptySet();
        }
        return set;
    }

    /**
     * 获取用户所有连接
     */
    public static Collection<Set<Channel>> getUserAllSession() {
        return USER_SESSIONS.values();
    }

    /**
     * 添加用户连接
     */
    public static boolean putUserSession(String uid, Channel channel) {
        SESSION_LINK.put(channel, uid);
        return USER_SESSIONS.put(uid, channel);
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
    public static boolean putRoomSession(String roomId, Channel channel) {
        SESSION_LINK.put(channel, roomId);
        ROOM_SESSIONS.put(roomId, channel);
        return true;
    }

    /**
     * 移除直播间连接
     */
    public static boolean removeRoomSession(String roomId, Channel channel) {
        ROOM_SESSIONS.remove(roomId, channel);
        SESSION_LINK.remove(channel, roomId);
        return true;
    }

    /**
     * 移除连接
     */
    public static void removeSession(Channel channel) {
        if (channel != null) {
            String uid = getUserKey(channel);
            Set<String> keys = SESSION_LINK.get(channel);
            if (keys == null) {
                keys = new HashSet<>();
            }
            if (uid != null) {
                keys.add(uid);
            }
            keys.forEach(key -> {
                USER_SESSIONS.remove(key, channel);
                ROOM_SESSIONS.remove(key, channel);
            });
            SESSION_LINK.removeKey(channel);
        }
    }

    /**
     * 连接关闭
     */
    public static void close(Channel channel) {
        if (channel != null) {
            try {
                channel.close();
            } catch (Exception e) {
                log.info("sessionClose error {}", getUserKey(channel), e);
            }
        }
    }

    /**
     * 踢出相同端的连接
     */
    public static void kickOut(Channel channel, String key, UserTokenDTO dto) {
        String clientName = dto.getClientName();
        if (Objects.equals(clientName, ClientNameEnum.WEB.getName())) {
            Set<Channel> userSession = getUserSession(key);
            long count = userSession.parallelStream().filter(s -> {
                UserTokenDTO tokenDTO = getUserTokenDTO(s);
                return tokenDTO != null && Objects.equals(tokenDTO.getClientName(), ClientNameEnum.WEB.getName());
            }).count();
            if (count > 10) {
                Optional<Channel> first = userSession.parallelStream().filter(s -> {
                    UserTokenDTO tokenDTO = getUserTokenDTO(s);
                    return tokenDTO != null && Objects.equals(tokenDTO.getClientName(), ClientNameEnum.WEB.getName()) && channel != s;
                }).findFirst();
                first.ifPresent(UserSessionHolder::close);
            }
        } else if (Objects.equals(clientName, ClientNameEnum.ANDROID.getName())) {
            getUserSession(key).parallelStream().iterator().forEachRemaining(s -> {
                UserTokenDTO tokenDTO = getUserTokenDTO(s);
                if (tokenDTO != null && s != channel && StringUtil.equals(tokenDTO.getClientName(), ClientNameEnum.ANDROID.getName())) {
                    close(channel);
                }
            });
        } else if (Objects.equals(clientName, ClientNameEnum.IOS.getName())) {
            getUserSession(key).parallelStream().iterator().forEachRemaining(s -> {
                UserTokenDTO tokenDTO = getUserTokenDTO(s);
                if (tokenDTO != null && s != channel && StringUtil.equals(tokenDTO.getClientName(), ClientNameEnum.IOS.getName())) {
                    close(channel);
                }
            });
        } else {
            close(channel);
        }
    }

    public static String getUserKey(Channel channel) {
        Attribute<String> attr = channel.attr(AttributeKey.valueOf(USER_KEY));
        return attr.get();
    }

    public static UserTokenDTO getUserTokenDTO(Channel channel) {
        Attribute<UserTokenDTO> attr = channel.attr(AttributeKey.valueOf(USER_INFO));
        return attr.get();
    }

}
