package piper.im.common.pojo.config;

import lombok.Data;

/**
 * 数据库配置
 *
 * @author piper
 */
@Data
public class DbConfig {
    private String url;
    private String user;
    private String password;
}
