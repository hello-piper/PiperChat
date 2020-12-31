package piper.im.netty;

/**
 * 消息内容类型枚举
 *
 * @author piper
 */
public enum MessageTypeEnum {

    // 文本消息
    text((byte) 0),
    // 图片
    image((byte) 1),
    // 语音
    voice((byte) 2),
    // 视频
    video((byte) 3),
    // 文件
    file((byte) 4),
    // 地理位置信息
    location((byte) 5),
    // 提示消息
    notice((byte) 10),
    ;

    public byte type;

    MessageTypeEnum(byte type) {
        this.type = type;
    }

    public static MessageTypeEnum valueOf(byte type) {
        MessageTypeEnum[] values = values();
        for (MessageTypeEnum value : values) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }

}
