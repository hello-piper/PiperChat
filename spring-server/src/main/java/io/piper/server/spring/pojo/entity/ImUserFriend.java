package io.piper.server.spring.pojo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ImUserFriend implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long uid;

    private Long friendId;

    private String alias;

    private Integer status;

    private String reqMsg;

    private Long createTime;
}