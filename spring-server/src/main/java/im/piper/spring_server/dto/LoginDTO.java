package im.piper.spring_server.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Long uid;
    private String pwd;
}
