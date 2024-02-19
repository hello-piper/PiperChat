package io.piper.server.spring.pojo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ImMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer msgType;

    private Integer chatType;

    private Long chatId;

    private Long from;

    private String to;

    private Long time;

    private String text;

    private String body;
}