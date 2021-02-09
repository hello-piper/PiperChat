package piper.im.common.pojo.message;

import piper.im.common.enums.MessageTypeEnum;

/**
 * 文字消息
 *
 * @author piper
 */
public class TextMessageBody extends MessageBody {

    /**
     * 文字
     */
    private String text;

    public TextMessageBody() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextMessageBody(String text) {
        this.text = text;
    }

    @Override
    public MessageTypeEnum getType() {
        return MessageTypeEnum.TEXT;
    }
}
