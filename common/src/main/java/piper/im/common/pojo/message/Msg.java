package piper.im.common.pojo.message;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.msgpack.annotation.Message;
import piper.im.common.enums.ChatTypeEnum;
import piper.im.common.enums.MsgTypeEnum;

import java.io.Serializable;
import java.util.Map;

/**
 * 聊天消息
 *
 * @author piper
 */
@Data
@Message
public class Msg implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 消息id
     */
    private Long id;

    /**
     * 消息类型
     */
    private Byte chatType;

    /**
     * 消息内容类型
     */
    private Byte msgType;

    /**
     * 发送者id
     */
    private String from;

    /**
     * 接收者id/群id/聊天室id
     */
    private String to;

    /**
     * 消息发送的时间
     */
    private Long timestamp;

    /**
     * 文本消息内容
     */
    private String text;

    /**
     * 图片消息内容
     */
    private ImageMsgBody imageMsgBody;

    /**
     * 语音消息内容
     */
    private VoiceMsgBody voiceMsgBody;

    /**
     * 视频消息内容
     */
    private VideoMsgBody videoMsgBody;

    /**
     * 文件消息内容
     */
    private FileMsgBody fileMsgBody;

    /**
     * 位置消息内容
     */
    private LocationMsgBody locationMsgBody;

    /**
     * 信令消息内容
     */
    private NotifyMsgBody notifyMsgBody;

    /**
     * 附加属性
     */
    private Map<String, String> extra;

    public Msg() {
    }

    public static Msg createTextMsg(String text) {
        Msg msg = new Msg();
        msg.setTimestamp(System.currentTimeMillis());
        msg.setMsgType(MsgTypeEnum.TEXT.type);
        msg.setText(text);
        return msg;
    }

    public static Msg createImageMsg(Double width, Double height, Long size, String imgUrl, String thumbnailUrl) {
        Msg msg = new Msg();
        msg.setTimestamp(System.currentTimeMillis());
        msg.setMsgType(MsgTypeEnum.IMAGE.type);
        msg.setImageMsgBody(new ImageMsgBody(width, height, size, imgUrl, thumbnailUrl));
        return msg;
    }

    public static Msg createVoiceMsg(String voiceUrl, int length, Long size) {
        Msg msg = new Msg();
        msg.setTimestamp(System.currentTimeMillis());
        msg.setMsgType(MsgTypeEnum.VOICE.type);
        msg.setVoiceMsgBody(new VoiceMsgBody(voiceUrl, length, size));
        return msg;
    }

    public static Msg createVideoMsg(String videoName, String videoUrl, int length, Long size) {
        Msg msg = new Msg();
        msg.setTimestamp(System.currentTimeMillis());
        msg.setMsgType(MsgTypeEnum.VIDEO.type);
        msg.setVideoMsgBody(new VideoMsgBody(videoName, videoUrl, length, size));
        return msg;
    }

    public static Msg createFileMsg(String fileName, String fileUrl, Long size) {
        Msg msg = new Msg();
        msg.setTimestamp(System.currentTimeMillis());
        msg.setMsgType(MsgTypeEnum.FILE.type);
        msg.setFileMsgBody(new FileMsgBody(fileName, fileUrl, size));
        return msg;
    }

    public static Msg createLocationMsg(String address, Double latitude, Double longitude) {
        Msg msg = new Msg();
        msg.setTimestamp(System.currentTimeMillis());
        msg.setMsgType(MsgTypeEnum.LOCATION.type);
        msg.setLocationMsgBody(new LocationMsgBody(address, latitude, longitude));
        return msg;
    }

    public static Msg createNotifyMsg(String type, Map<String, String> params) {
        Msg msg = new Msg();
        msg.setTimestamp(System.currentTimeMillis());
        msg.setMsgType(MsgTypeEnum.NOTIFY.type);
        msg.setNotifyMsgBody(new NotifyMsgBody(type, params));
        return msg;
    }

    public ChatTypeEnum getChatTypeEnum() {
        return ChatTypeEnum.valueOf(this.getChatType());
    }

    public MsgTypeEnum getMsgTypeEnum() {
        return MsgTypeEnum.valueOf(this.getMsgType());
    }

    public String getBodyStr() {
        MsgTypeEnum msgTypeEnum = this.getMsgTypeEnum();
        switch (msgTypeEnum) {
            case TEXT:
                return this.text;
            case IMAGE:
                return JSONUtil.toJsonStr(this.imageMsgBody);
            case VOICE:
                return JSONUtil.toJsonStr(this.voiceMsgBody);
            case VIDEO:
                return JSONUtil.toJsonStr(this.videoMsgBody);
            case FILE:
                return JSONUtil.toJsonStr(this.fileMsgBody);
            case LOCATION:
                return JSONUtil.toJsonStr(this.locationMsgBody);
            case NOTIFY:
                return JSONUtil.toJsonStr(this.notifyMsgBody);
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
