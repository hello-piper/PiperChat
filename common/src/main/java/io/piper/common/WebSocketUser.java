package io.piper.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户信息保持
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

    public static void addGroupChannel(String uid, Object channel) {
        CHANNEL_BY_GROUP_MAP.computeIfAbsent(uid, v -> new ArrayList<>()).add(channel);
    }

    public static <T> List<T> getGroupChannels(String uid) {
        return (List<T>) CHANNEL_BY_GROUP_MAP.get(uid);
    }

    public static void removeGroupChannel(String uid, Object channel) {
        if (channel == null || uid == null) {
            return;
        }
        List<Object> channels = CHANNEL_BY_GROUP_MAP.get(uid);
        if (channels == null || channels.isEmpty()) {
            return;
        }
        channels.remove(channel);
    }

}
