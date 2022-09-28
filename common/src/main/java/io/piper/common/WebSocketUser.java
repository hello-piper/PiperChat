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
package io.piper.common;

import io.piper.common.util.HashMultiMap;

import java.util.HashSet;
import java.util.Set;

/**
 * WebSocketUser
 *
 * @author piper
 */
public class WebSocketUser {
    private static final HashMultiMap<Long, Object> CHANNEL_MAP = new HashMultiMap<>(10000);
    private static final HashMultiMap<Long, Object> CHANNEL_ROOM_MAP = new HashMultiMap<>(10000);
    private static final HashMultiMap<Object, Long> SESSION_LINK = new HashMultiMap<>(10000);

    // single

    public static void put(Long uid, Object channel) {
        SESSION_LINK.put(channel, uid);
        CHANNEL_MAP.put(uid, channel);
    }

    public static <T> Set<T> get(Long uid) {
        return (Set<T>) CHANNEL_MAP.get(uid);
    }

    public static void remove(Long uid, Object channel) {
        CHANNEL_MAP.remove(uid, channel);
    }

    public static Integer onlineNum() {
        return CHANNEL_MAP.valueSize();
    }

    // chatRoom

    public static void putRoomChannel(Long roomId, Object channel) {
        SESSION_LINK.put(channel, roomId);
        CHANNEL_ROOM_MAP.put(roomId, channel);
    }

    public static <T> Set<T> getRoomChannels(Long roomId) {
        return (Set<T>) CHANNEL_ROOM_MAP.get(roomId);
    }

    public static void removeRoomChannel(Long roomId, Object channel) {
        CHANNEL_ROOM_MAP.remove(roomId, channel);
    }

    public static int roomNum() {
        return CHANNEL_MAP.keySize();
    }

    public static int roomUserNum(Long roomId) {
        Set<Object> set = CHANNEL_ROOM_MAP.get(roomId);
        if (set != null) {
            return set.size();
        }
        return 0;
    }

    public static boolean removeChannel(Object channel) {
        if (channel != null) {
            Set<Long> keys = SESSION_LINK.get(channel);
            if (keys == null) {
                keys = new HashSet<>();
            }
            keys.forEach(key -> {
                CHANNEL_MAP.remove(key, channel);
                CHANNEL_ROOM_MAP.remove(key, channel);
            });
            return true;
        }
        return false;
    }

}
