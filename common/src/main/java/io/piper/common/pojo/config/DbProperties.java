package io.piper.common.pojo.config;

import lombok.Data;

/**
 * 数据库配置
 *
 * @author piper
 */
@Data
public class DbProperties {
    private String driver;
    private String url;
    private String username;
    private String password;
}
