package piper.im.common.pojo.message;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Map;

/**
 * 聊天消息
 *
 * @author piper
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 消息id
     */
    private String msgId;

    /**
     * 消息类型
     */
    private Byte chatType;

    /**
     * 发送者id
     */
    private String from;

    /**
     * 接收者id/群id/聊天室id
     */
    private String to;

    /**
     * 消息内容
     */
    private MessageBody body;

    public static Message createTextMessage(String text) {
        Message message = new Message();
        message.setBody(new TextMessageBody(text));
        return message;
    }

    public static Message createImageMessage(double width, double height, long size, String imgUrl, String thumbnailUrl) {
        Message message = new Message();
        message.setBody(new ImageMessageBody(width, height, size, imgUrl, thumbnailUrl));
        return message;
    }

    public static Message createFileMessage(String fileName, String fileUrl, long size) {
        Message message = new Message();
        message.setBody(new FileMessageBody(fileName, fileUrl, size));
        return message;
    }

    public static Message createVoiceMessage(String voiceUrl, int length, long size) {
        Message message = new Message();
        message.setBody(new VoiceMessageBody(voiceUrl, length, size));
        return message;
    }

    public static Message createVideoMessage(String videoName, String videoUrl, int length, long size) {
        Message message = new Message();
        message.setBody(new VideoMessageBody(videoName, videoUrl, length, size));
        return message;
    }

    public static Message createLocationMessage(String address, double latitude, double longitude) {
        Message message = new Message();
        message.setBody(new LocationMessageBody(address, latitude, longitude));
        return message;
    }

    public static Message createCmdMessage(String action, Map<String, String> params) {
        Message message = new Message();
        message.setBody(new CmdMessageBody(action, params));
        return message;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Byte getChatType() {
        return chatType;
    }

    public void setChatType(Byte chatType) {
        this.chatType = chatType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public MessageBody getBody() {
        return body;
    }

    public void setBody(MessageBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
