package io.piper.common.pojo.config;

import lombok.Data;

/**
 * Jwt配置类
 *
 * @author piper
 * @date 2021年6月29日 08点00分
 */
@Data
public class JwtProperties {

    /**
     * jwt的秘钥
     */
    private String secret = "7Rv9OTzS";

    /**
     * jwt过期时间(单位:小时)
     */
    private Long expireHour = 24L;

    /**
     * header中标识jwt的字段名称
     */
    private String name = "token";
}
