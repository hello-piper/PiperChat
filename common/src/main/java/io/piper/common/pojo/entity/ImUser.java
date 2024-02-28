package io.piper.common.pojo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ImUser implements Serializable {
    private Long id;

    private String nickname;

    private String avatar;

    private String phone;

    private String email;

    private Integer age;

    private Integer gender;

    private String password;

    private String salt;

    private Integer status;

    private Long createUid;

    private Long createTime;

    private static final long serialVersionUID = 1L;
}