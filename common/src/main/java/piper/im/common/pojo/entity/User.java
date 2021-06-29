package piper.im.common.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 *
 * @author piper
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1;

    private String id;

    private String nickname;

    private String avatar;

    private String phone;

    private String email;

    private Integer age;

    private Integer gender;

    private String password;

    private String salt;

    private Integer status;

    private String createUser;

    private String createTime;
}
