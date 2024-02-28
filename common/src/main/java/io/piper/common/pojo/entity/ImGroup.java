package io.piper.common.pojo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ImGroup implements Serializable {
    private Long id;

    private String name;

    private String avatar;

    private String placard;

    private Integer status;

    private Long createUid;

    private Long createTime;

    private static final long serialVersionUID = 1L;
}