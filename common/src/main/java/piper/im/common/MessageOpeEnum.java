package piper.im.common;

/**
 * 消息操作类型枚举
 *
 * @author piper
 */
public enum MessageOpeEnum {

    // 0：点对点个人消息
    peerToPeer((byte) 0),
    // 1：群消息（高级群）
    group((byte) 1);

    public byte type;

    MessageOpeEnum(byte type) {
        this.type = type;
    }

    public static MessageOpeEnum valueOf(byte type) {
        MessageOpeEnum[] values = values();
        for (MessageOpeEnum value : values) {
            if (value.type == type) {
                return value;
            }
        }
        return null;
    }

}
