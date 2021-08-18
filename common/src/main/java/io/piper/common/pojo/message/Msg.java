/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.common.pojo.message;

import cn.hutool.json.JSONUtil;
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.enums.MsgTypeEnum;
import lombok.Data;
import org.msgpack.annotation.Message;

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
     * 会话类型
     */
    private Byte chatType;

    /**
     * 消息类型
     */
    private Byte msgType;

    /**
     * 会话id
     */
    private String conversationId;

    /**
     * 发送者id
     */
    private Long from;

    /**
     * 发送者昵称
     */
    private String fromNickname;

    /**
     * 接收者id/群id/聊天室id
     */
    private Long to;

    /**
     * 消息发送时间
     */
    private Long sendTime;

    /**
     * 服务器时间
     */
    private Long serverTime;

    /**
     * 消息标题/文本消息内容
     */
    private String title;

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
    private CmdMsgBody cmdMsgBody;

    /**
     * 附加属性
     */
    private Map<String, String> extra;

    public Msg() {
    }

    public static Msg createTextMsg(String text) {
        Msg msg = new Msg();
        msg.setMsgType(MsgTypeEnum.TEXT.type);
        msg.setTitle(text);
        return msg;
    }

    public static Msg createImageMsg(Double width, Double height, Long size, String imgUrl, String thumbnailUrl) {
        Msg msg = new Msg();
        msg.setMsgType(MsgTypeEnum.IMAGE.type);
        msg.setImageMsgBody(new ImageMsgBody(width, height, size, imgUrl, thumbnailUrl));
        return msg;
    }

    public static Msg createVoiceMsg(String voiceUrl, int length, Long size) {
        Msg msg = new Msg();
        msg.setMsgType(MsgTypeEnum.VOICE.type);
        msg.setVoiceMsgBody(new VoiceMsgBody(voiceUrl, length, size));
        return msg;
    }

    public static Msg createVideoMsg(String videoName, String videoUrl, int length, Long size) {
        Msg msg = new Msg();
        msg.setMsgType(MsgTypeEnum.VIDEO.type);
        msg.setVideoMsgBody(new VideoMsgBody(videoName, videoUrl, length, size));
        return msg;
    }

    public static Msg createFileMsg(String fileName, String fileUrl, Long size) {
        Msg msg = new Msg();
        msg.setMsgType(MsgTypeEnum.FILE.type);
        msg.setFileMsgBody(new FileMsgBody(fileName, fileUrl, size));
        return msg;
    }

    public static Msg createLocationMsg(String address, Double latitude, Double longitude) {
        Msg msg = new Msg();
        msg.setMsgType(MsgTypeEnum.LOCATION.type);
        msg.setLocationMsgBody(new LocationMsgBody(address, latitude, longitude));
        return msg;
    }

    public static Msg createNotifyMsg(Byte type, Map<String, String> params) {
        Msg msg = new Msg();
        msg.setMsgType(MsgTypeEnum.CMD.type);
        msg.setCmdMsgBody(new CmdMsgBody(type, params));
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
                return this.title;
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
            case CMD:
                return JSONUtil.toJsonStr(this.cmdMsgBody);
            default:
                return null;
        }
    }

    public String getAndSetConversation() {
        String conversationId = getConversation(this.chatType, this.from, this.to);
        this.setConversationId(conversationId);
        return conversationId;
    }

    public static String getConversation(Byte chatType, Long from, Long to) {
        if (!ChatTypeEnum.SINGLE.type.equals(chatType)) {
            return to.toString();
        }
        if (from < to) {
            return from + ":" + to;
        }
        return to + ":" + from;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
