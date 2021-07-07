package piper.im.common.enums;

import java.util.Arrays;

/**
 * 聊天类型枚举
 *
 * @author piper
 */
public enum ChatTypeEnum {
    // 单聊
    SINGLE((byte) 0),
    // 群聊
    GROUP((byte) 1),
    // 聊天室
    CHATROOM((byte) 2);

    public Byte type;

    ChatTypeEnum(Byte type) {
        this.type = type;
    }

    public static ChatTypeEnum valueOf(Byte type) {
        return Arrays.stream(values()).filter(v -> v.type.equals(type)).findFirst().orElse(null);
    }
}
