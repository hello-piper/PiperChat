package io.piper.common.pojo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ImGroupUser implements Serializable {
    private Long id;

    private Long uid;

    private Long groupId;

    private Integer status;

    private Long createUid;

    private Long createTime;

    private static final long serialVersionUID = 1L;
}