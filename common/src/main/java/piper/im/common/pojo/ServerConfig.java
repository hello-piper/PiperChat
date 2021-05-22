package piper.im.common.pojo;

import java.io.Serializable;

/**
 * 服务器配置
 *
 * @author piper
 */
public class ServerConfig implements Serializable {
    private static final long serialVersionUID = 1;

    private Boolean ssl = false;

    private Integer port;

    private String wsPath;

    private String httpPath;

    private String reportUrl;

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getWsPath() {
        return wsPath;
    }

    public void setWsPath(String wsPath) {
        this.wsPath = wsPath;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    public String getReportUrl() {
        return reportUrl;
    }

    public void setReportUrl(String reportUrl) {
        this.reportUrl = reportUrl;
    }
}
