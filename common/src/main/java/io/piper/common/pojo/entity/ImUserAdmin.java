package io.piper.common.pojo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ImUserAdmin implements Serializable {
    private Long uid;

    private Integer status;

    private Long createUid;

    private Long createTime;

    private static final long serialVersionUID = 1L;
}