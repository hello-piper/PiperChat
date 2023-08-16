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
package io.piper.common.spi;

import io.piper.common.constant.ClientNameEnum;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.util.HashMultiMap;
import io.piper.common.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * AbstractImUserHolder
 *
 * @author piper
 */
public class AbstractImUserHolder<T> {
    public static final Logger log = LoggerFactory.getLogger(AbstractImUserHolder.class);
    public static final String USER_KEY = "key";
    public static final String USER_INFO = "userInfo";

    // 用户的连接
    private final HashMultiMap<Long, T> USER_SESSIONS = new HashMultiMap<>();
    // 在直播间内的用户连接
    private final HashMultiMap<Long, T> ROOM_SESSIONS = new HashMultiMap<>();
    // 用户的连接在上边哪些key里面
    private final HashMultiMap<T, Long> SESSION_LINK = new HashMultiMap<>();

    public static final AbstractImUserHolder INSTANCE = new AbstractImUserHolder();

    public Long getUserKey(T channel) {
        return null;
    }

    public UserTokenDTO getUserTokenDTO(T channel) {
        return null;
    }

    /**
     * 根据uid获取连接
     */
    public Set<T> getUserSession(Long uid) {
        Set<T> set = USER_SESSIONS.get(uid);
        if (set == null) {
            return Collections.emptySet();
        }
        return set;
    }

    /**
     * 根据uid获取直播间连接
     */
    public Set<T> getRoomSession(Long roomId) {
        Set<T> set = ROOM_SESSIONS.get(roomId);
        if (set == null) {
            return Collections.emptySet();
        }
        return set;
    }

    /**
     * 获取用户所有连接
     */
    public Collection<Set<T>> getUserAllSession() {
        return USER_SESSIONS.values();
    }

    /**
     * 添加用户连接
     */
    public boolean putUserSession(Long uid, T channel) {
        SESSION_LINK.put(channel, uid);
        return USER_SESSIONS.put(uid, channel);
    }

    /**
     * 连接数
     */
    public Integer onlineNum() {
        return USER_SESSIONS.valueSize();
    }

    /**
     * 添加直播间连接
     */
    public boolean putRoomSession(Long roomId, T channel) {
        SESSION_LINK.put(channel, roomId);
        return ROOM_SESSIONS.put(roomId, channel);
    }

    /**
     * 移除直播间连接
     */
    public boolean removeRoomSession(Long roomId, T channel) {
        ROOM_SESSIONS.remove(roomId, channel);
        return SESSION_LINK.remove(channel, roomId);
    }

    /**
     * 移除连接
     */
    public void removeSession(T channel) {
        if (channel != null) {
            Long uid = getUserKey(channel);
            Set<Long> keys = SESSION_LINK.get(channel);
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
    public void close(T channel) {
    }

    /**
     * 踢出相同端的连接
     */
    public void kickOut(T channel, Long key, UserTokenDTO dto) {
        String clientName = dto.getClientName();
        if (Objects.equals(clientName, ClientNameEnum.WEB.getName())) {
            Set<T> userSession = getUserSession(key);
            long count = userSession.parallelStream().filter(s -> {
                UserTokenDTO tokenDTO = getUserTokenDTO(s);
                return tokenDTO != null && Objects.equals(tokenDTO.getClientName(), ClientNameEnum.WEB.getName());
            }).count();
            if (count > 10) {
                Optional<T> first = userSession.parallelStream().filter(s -> {
                    UserTokenDTO tokenDTO = getUserTokenDTO(s);
                    return tokenDTO != null && Objects.equals(tokenDTO.getClientName(), ClientNameEnum.WEB.getName()) && channel != s;
                }).findFirst();
                first.ifPresent(this::close);
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

}
