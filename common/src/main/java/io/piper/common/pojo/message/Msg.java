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

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.enums.MsgTypeEnum;
import lombok.Data;

/**
 * Msg
 * @author piper
 */
@Data
public class Msg implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * id
     */
    private Long id;

    /**
     * type
     */
    private Integer type;

    /**
     * chatType
     */
    private Integer chatType;

    /**
     * chatId conversationId/roomId/groupId
     */
    private Long chatId;

    /**
     * from
     */
    private Long from;

    /**
     * to，[0-100]
     */
    private List<Long> to;

    /**
     * text/desc
     */
    private String text;

    /**
     * body
     */
    private IMsgBody body;

    /**
     * seconds
     */
    private Long time;

    public Msg() {
    }

    public static Msg createTextMsg(String text) {
        Msg msg = new Msg();
        msg.setType(MsgTypeEnum.TEXT.type);
        msg.setText(text);
        return msg;
    }

    public static Msg createImageMsg(Integer width, Integer height, Integer size, String imgUrl, String thumbnailUrl) {
        Msg msg = new Msg();
        msg.setType(MsgTypeEnum.IMAGE.type);
        msg.setBody(new ImageMsgBody(width, height, size, imgUrl, thumbnailUrl));
        return msg;
    }

    public static Msg createAudioMsg(String voiceUrl, int seconds, Integer size) {
        Msg msg = new Msg();
        msg.setType(MsgTypeEnum.AUDIO.type);
        msg.setBody(new AudioMsgBody(voiceUrl, seconds, size));
        return msg;
    }

    public static Msg createVideoMsg(String videoName, String videoUrl, int seconds, Integer size) {
        Msg msg = new Msg();
        msg.setType(MsgTypeEnum.VIDEO.type);
        msg.setBody(new VideoMsgBody(videoName, videoUrl, seconds, size));
        return msg;
    }

    public static Msg createFileMsg(String fileName, String fileUrl, Integer size) {
        Msg msg = new Msg();
        msg.setType(MsgTypeEnum.FILE.type);
        msg.setBody(new FileMsgBody(fileName, fileUrl, size));
        return msg;
    }

    public static Msg createLocationMsg(String address, Double latitude, Double longitude) {
        Msg msg = new Msg();
        msg.setType(MsgTypeEnum.LOCATION.type);
        msg.setBody(new LocationMsgBody(address, latitude, longitude));
        return msg;
    }

    public static Msg createCustomMsg(String data) {
        Msg msg = new Msg();
        msg.setType(MsgTypeEnum.CUSTOM.type);
        msg.setBody(new CustomMsgBody(data));
        return msg;
    }

    public MsgTypeEnum getTypeEnum() {
        return MsgTypeEnum.valueOf(this.getType());
    }

    public ChatTypeEnum getChatTypeEnum() {
        return ChatTypeEnum.valueOf(this.getChatType());
    }

    public String getBodyStr() {
        switch (this.getTypeEnum()) {
            case IMAGE:
            case AUDIO:
            case VIDEO:
            case FILE:
            case LOCATION:
            case CUSTOM:
                return JSON.toJSONString(this.body);
            case TEXT:
            default:
                return null;
        }
    }

    public boolean valid() {
        return this.getTo() != null && this.getFrom() != null && this.getType() != null && this.getChatType() != null;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    /**
     * 消息体
     */
    public interface IMsgBody {
    }
}
