package piper.im.common.pojo.message;

import java.io.Serializable;

/**
 * 文字消息
 *
 * @author piper
 */
public class TextMessageBody implements Serializable {
    private static final long serialVersionUID = 1;

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
}
