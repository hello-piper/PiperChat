package piper.im.common;

import java.io.Serializable;
import java.util.Objects;

/**
 * 服务器地址
 *
 * @author piper
 */
public class AddressInfo implements Serializable {
    private static final long serialVersionUID = 1;

    private Boolean ssl = false;

    private String ip;

    private String port;

    private String wsPath;

    private String httpPath;

    private Integer onlineNum;

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
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

    public Integer getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(Integer onlineNum) {
        this.onlineNum = onlineNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressInfo that = (AddressInfo) o;
        return ip.equals(that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip);
    }

    @Override
    public String toString() {
        return "{" +
                "ssl=" + ssl +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", wsPath='" + wsPath + '\'' +
                ", httpPath='" + httpPath + '\'' +
                ", onlineNum=" + onlineNum +
                '}';
    }
}
