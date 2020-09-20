package piper.im.jsr.undertow;

import java.io.Serializable;

@org.msgpack.annotation.Message
public class Message implements Serializable {
    private Byte type;
    private String content;

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", content='" + content + '\'' +
                '}';
    }
}
