package piper.im.common.enums;

import java.util.Arrays;

/**
 * 消息类型枚举
 *
 * @author piper
 */
public enum ChatTypeEnum {

    // 单聊
    SINGLE(0),
    // 群聊
    GROUP(1),
    // 聊天室
    CHATROOM(2);

    public int type;

    ChatTypeEnum(int type) {
        this.type = type;
    }

    public static ChatTypeEnum valueOf(int type) {
        return Arrays.stream(values()).filter(v -> v.type == type).findFirst().orElse(null);
    }
}
