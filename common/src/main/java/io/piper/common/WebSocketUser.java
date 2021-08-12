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

import io.piper.common.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketUser
 *
 * @author piper
 */
public class WebSocketUser {
    private static final Map<String, Object> CHANNEL_MAP = new ConcurrentHashMap<>();
    private static final Map<String, List<Object>> CHANNEL_BY_GROUP_MAP = new ConcurrentHashMap<>();

    // peer to peer

    public static void put(String uid, Object channel) {
        CHANNEL_MAP.put(uid, channel);
    }

    public static <T> T get(String uid) {
        return (T) CHANNEL_MAP.get(uid);
    }

    public static void remove(String uid) {
        if (uid == null) {
            return;
        }
        CHANNEL_MAP.remove(uid);
    }

    public static Integer onlineNum() {
        return CHANNEL_MAP.size();
    }

    // group

    public static void addGroupChannel(String groupId, Object channel) {
        CHANNEL_BY_GROUP_MAP.computeIfAbsent(groupId, v -> new ArrayList<>()).add(channel);
    }

    public static <T> List<T> getGroupChannels(String groupId) {
        return (List<T>) CHANNEL_BY_GROUP_MAP.get(groupId);
    }

    public static void removeGroupChannel(String groupId, Object channel) {
        if (StringUtil.isAnyEmpty(groupId, channel)) {
            return;
        }
        List<Object> channels = CHANNEL_BY_GROUP_MAP.get(groupId);
        if (channels == null || channels.isEmpty()) {
            return;
        }
        channels.remove(channel);
    }

}
