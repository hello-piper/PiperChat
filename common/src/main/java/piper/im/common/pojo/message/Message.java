package piper.im.common.pojo.message;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import piper.im.common.enums.MessageTypeEnum;

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
    private String id;

    /**
     * 消息类型
     */
    private Byte chatType;

    /**
     * 消息内容类型
     */
    private Byte messageType;

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
    private Object body;

    private Message() {
    }

    public static Message createTextMessage(String text) {
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.TEXT.type);
        message.setBody(new TextMessageBody(text));
        return message;
    }

    public static Message createImageMessage(double width, double height, long size, String imgUrl, String thumbnailUrl) {
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.IMAGE.type);
        message.setBody(new ImageMessageBody(width, height, size, imgUrl, thumbnailUrl));
        return message;
    }

    public static Message createFileMessage(String fileName, String fileUrl, long size) {
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.FILE.type);
        message.setBody(new FileMessageBody(fileName, fileUrl, size));
        return message;
    }

    public static Message createVoiceMessage(String voiceUrl, int length, long size) {
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.VOICE.type);
        message.setBody(new VoiceMessageBody(voiceUrl, length, size));
        return message;
    }

    public static Message createVideoMessage(String videoName, String videoUrl, int length, long size) {
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.VIDEO.type);
        message.setBody(new VideoMessageBody(videoName, videoUrl, length, size));
        return message;
    }

    public static Message createLocationMessage(String address, double latitude, double longitude) {
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.LOCATION.type);
        message.setBody(new LocationMessageBody(address, latitude, longitude));
        return message;
    }

    public static Message createCmdMessage(String action, Map<String, String> params) {
        Message message = new Message();
        message.setMessageType(MessageTypeEnum.COMMAND.type);
        message.setBody(new CmdMessageBody(action, params));
        return message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Byte getChatType() {
        return chatType;
    }

    public void setChatType(Byte chatType) {
        this.chatType = chatType;
    }

    public Byte getMessageType() {
        return messageType;
    }

    public void setMessageType(Byte messageType) {
        this.messageType = messageType;
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

    public Object getBody() {
        return body;
    }

    public <T> T getBodyByMsgType(Class<T> clz) {
        if (this.body instanceof JSONObject) {
            JSONObject body = (JSONObject) this.body;
            return body.toJavaObject(clz);
        }
        return BeanUtil.toBean(this.body, clz);
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
