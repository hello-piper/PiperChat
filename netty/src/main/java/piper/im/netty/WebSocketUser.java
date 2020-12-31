package piper.im.netty;

import io.netty.channel.Channel;

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
    private static final Map<String, Channel> CHANNEL_MAP = new ConcurrentHashMap<>();
    private static final Map<String, List<Channel>> CHANNEL_BY_GROUP_MAP = new ConcurrentHashMap<>();

    // peer to peer

    public static void put(String uid, Channel channel) {
        CHANNEL_MAP.put(uid, channel);
    }

    public static Channel get(String uid) {
        return CHANNEL_MAP.get(uid);
    }

    public static void remove(String uid) {
        if (uid == null) {
            return;
        }
        CHANNEL_MAP.remove(uid);
    }

    // group

    public static void addGroupChannel(String uid, Channel channel) {
        CHANNEL_BY_GROUP_MAP.computeIfAbsent(uid, v -> new ArrayList<>()).add(channel);
    }

    public static List<Channel> getGroupChannels(String uid) {
        return CHANNEL_BY_GROUP_MAP.get(uid);
    }

    public static void removeGroupChannel(String uid, Channel channel) {
        if (channel == null || uid == null) {
            return;
        }
        List<Channel> channels = CHANNEL_BY_GROUP_MAP.get(uid);
        if (channels == null || channels.isEmpty()) {
            return;
        }
        channels.remove(channel);
    }

}
