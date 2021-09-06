package io.piper.server.spring.pojo.entity;

import java.io.Serializable;

public class ImMessage implements Serializable {
    private Long id;

    private Byte chatType;

    private Byte msgType;

    private Long from;

    private Long to;

    private String conversationId;

    private Long sendTime;

    private Long serverTime;

    private String title;

    private String imageMsgBody;

    private String voiceMsgBody;

    private String videoMsgBody;

    private String fileMsgBody;

    private String locationMsgBody;

    private String cmdMsgBody;

    private String extra;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getChatType() {
        return chatType;
    }

    public void setChatType(Byte chatType) {
        this.chatType = chatType;
    }

    public Byte getMsgType() {
        return msgType;
    }

    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId == null ? null : conversationId.trim();
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public Long getServerTime() {
        return serverTime;
    }

    public void setServerTime(Long serverTime) {
        this.serverTime = serverTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getImageMsgBody() {
        return imageMsgBody;
    }

    public void setImageMsgBody(String imageMsgBody) {
        this.imageMsgBody = imageMsgBody == null ? null : imageMsgBody.trim();
    }

    public String getVoiceMsgBody() {
        return voiceMsgBody;
    }

    public void setVoiceMsgBody(String voiceMsgBody) {
        this.voiceMsgBody = voiceMsgBody == null ? null : voiceMsgBody.trim();
    }

    public String getVideoMsgBody() {
        return videoMsgBody;
    }

    public void setVideoMsgBody(String videoMsgBody) {
        this.videoMsgBody = videoMsgBody == null ? null : videoMsgBody.trim();
    }

    public String getFileMsgBody() {
        return fileMsgBody;
    }

    public void setFileMsgBody(String fileMsgBody) {
        this.fileMsgBody = fileMsgBody == null ? null : fileMsgBody.trim();
    }

    public String getLocationMsgBody() {
        return locationMsgBody;
    }

    public void setLocationMsgBody(String locationMsgBody) {
        this.locationMsgBody = locationMsgBody == null ? null : locationMsgBody.trim();
    }

    public String getCmdMsgBody() {
        return cmdMsgBody;
    }

    public void setCmdMsgBody(String cmdMsgBody) {
        this.cmdMsgBody = cmdMsgBody == null ? null : cmdMsgBody.trim();
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", chatType=").append(chatType);
        sb.append(", msgType=").append(msgType);
        sb.append(", from=").append(from);
        sb.append(", to=").append(to);
        sb.append(", conversationId=").append(conversationId);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", serverTime=").append(serverTime);
        sb.append(", title=").append(title);
        sb.append(", imageMsgBody=").append(imageMsgBody);
        sb.append(", voiceMsgBody=").append(voiceMsgBody);
        sb.append(", videoMsgBody=").append(videoMsgBody);
        sb.append(", fileMsgBody=").append(fileMsgBody);
        sb.append(", locationMsgBody=").append(locationMsgBody);
        sb.append(", cmdMsgBody=").append(cmdMsgBody);
        sb.append(", extra=").append(extra);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}