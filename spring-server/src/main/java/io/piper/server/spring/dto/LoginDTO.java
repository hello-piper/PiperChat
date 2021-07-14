package io.piper.server.spring.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Long uid;
    private String pwd;
}
