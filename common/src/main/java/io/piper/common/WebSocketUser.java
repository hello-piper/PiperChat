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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketUser
 *
 * @author piper
 */
public class WebSocketUser {
    private static final Map<String, Object> CHANNEL_MAP = new ConcurrentHashMap<>();
    private static final Map<Long, Set<Object>> CHANNEL_ROOM_MAP = new ConcurrentHashMap<>();

    // peer to peer

    public static void put(String uid, Object channel) {
        CHANNEL_MAP.put(uid, channel);
    }

    public static <T> T get(String uid) {
        return (T) CHANNEL_MAP.get(uid);
    }

    public static void remove(String uid) {
        CHANNEL_MAP.remove(uid);
    }

    public static Integer onlineNum() {
        return CHANNEL_MAP.size();
    }

    // chatRoom

    public static void putRoomChannel(Long roomId, Object channel) {
        CHANNEL_ROOM_MAP.computeIfAbsent(roomId, v -> new HashSet<>()).add(channel);
    }

    public static <T> Set<T> getRoomChannels(Long roomId) {
        return (Set<T>) CHANNEL_ROOM_MAP.get(roomId);
    }

    public static void removeRoomChannel(Object channel) {
        CHANNEL_ROOM_MAP.values().forEach(channels -> {
            if (channels != null) {
                channels.remove(channel);
            }
        });
    }

    public static void removeRoomChannel(Long roomId, Object channel) {
        Set<Object> channels = CHANNEL_ROOM_MAP.get(roomId);
        if (channels != null) {
            channels.remove(channel);
        }
    }
}
