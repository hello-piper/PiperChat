package piper.im.common.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserBasicDTO implements Serializable {
    private static final long serialVersionUID = 1;

    private Long id;

    private String nickname;

    private String avatar;

    private String phone;

    private String clientType;
}
