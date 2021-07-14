package io.piper.common.pojo.config;

import java.io.Serializable;

/**
 * 获取本机IP地址 返回结果
 * 请求地址 https://ip.dianduidian.com/
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
     * 运营商
     */
    private String isp;
    /**
     * 国家
     */
    private String country;
    /**
     * 城市
     */
    private String city;
    /**
     * 区
     */
    private String region;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIsp() {
        return isp;
    }

    public void setIsp(String isp) {
        this.isp = isp;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
