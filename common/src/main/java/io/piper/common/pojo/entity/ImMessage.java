package io.piper.common.pojo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ImMessage implements Serializable {
    private Long id;

    private Integer chatType;

    private Integer msgType;

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
}