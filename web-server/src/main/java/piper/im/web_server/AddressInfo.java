package piper.im.web_server;

import java.util.Objects;

public class AddressInfo {

    private String ip;

    private String port;

    private String containPath;

    private Integer onlineNum;

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

    public String getContainPath() {
        return containPath;
    }

    public void setContainPath(String containPath) {
        this.containPath = containPath;
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
        return ip.equals(that.ip) && port.equals(that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port);
    }
}
