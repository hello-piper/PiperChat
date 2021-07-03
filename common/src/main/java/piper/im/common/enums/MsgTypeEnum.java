package piper.im.common.enums;

import java.util.Arrays;

/**
 * 消息内容类型枚举
 *
 * @author piper
 */
public enum MsgTypeEnum {
    // 文本消息
    TEXT((byte) 0),
    // 图片
    IMAGE((byte) 1),
    // 语音
    VOICE((byte) 2),
    // 视频
    VIDEO((byte) 3),
    // 文件
    FILE((byte) 4),
    // 地理位置信息
    LOCATION((byte) 5),
    // 通知
    NOTIFY((byte) 6);

    public Byte type;

    MsgTypeEnum(Byte type) {
        this.type = type;
    }

    public static MsgTypeEnum valueOf(Byte type) {
        return Arrays.stream(values()).filter(v -> v.type.equals(type)).findFirst().orElse(null);
    }
}
