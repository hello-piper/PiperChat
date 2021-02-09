package piper.im.common.enums;

import java.util.Arrays;

/**
 * 消息内容类型枚举
 *
 * @author piper
 */
public enum MessageTypeEnum {

    // 文本消息
    TEXT(0),
    // 图片
    IMAGE(1),
    // 语音
    VOICE(2),
    // 视频
    VIDEO(3),
    // 文件
    FILE(4),
    // 地理位置信息
    LOCATION(5),
    // 信令
    COMMAND(10);

    public int type;

    MessageTypeEnum(int type) {
        this.type = type;
    }

    public static MessageTypeEnum valueOf(int type) {
        return Arrays.stream(values()).filter(v -> v.type == type).findFirst().orElse(null);
    }
}
