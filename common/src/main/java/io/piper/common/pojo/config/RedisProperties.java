package io.piper.common.pojo.config;

import lombok.Data;

/**
 * Redis配置
 *
 * @author piper
 */
@Data
public class RedisProperties {
    private String host;
    private int port;
    private String password;
    private Integer timeout;
    private int maxActive;
    private long maxWait;
    private int maxIdle;
    private int minIdle;
}
