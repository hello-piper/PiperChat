package piper.im.common.pojo;

import java.io.Serializable;

/**
 * IP返回结果
 *
 * @author piper
 */
public class IpVO implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * IP地址
     */
    private String ip;
    /**
     * 省
     */
    private String pro;
    /**
     * 省编码
     */
    private String proCode;
    /**
     * 城市
     */
    private String city;
    /**
     * 城市编码
     */
    private String cityCode;
    /**
     * 区
     */
    private String region;
    /**
     * 区编码
     */
    private String regionCode;
    /**
     * 详细地址 + 运营商
     */
    private String addr;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
