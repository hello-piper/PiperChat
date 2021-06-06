package piper.im.common.pojo;

import java.io.Serializable;

/**
 * 用户
 *
 * @author piper
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1;

    private String id;

    private String nickname;

    private String phone;

    private String email;

    private Integer age;

    private Integer gender;

    private Integer status;

    private String createUser;

    private String createTime;
}
