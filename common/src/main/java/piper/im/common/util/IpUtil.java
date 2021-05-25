package piper.im.common.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import piper.im.common.pojo.IpVO;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * IP工具类
 *
 * @author piper
 */
public class IpUtil {
    /**
     * 获取访问者的ip地址
     * 注：要外网访问才能获取到外网地址，如果你在局域网甚至本机上访问，获得的是内网或者本机的ip
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            //X-Forwarded-For：Squid 服务代理
            String ipAddresses = request.getHeader("X-Forwarded-For");

            if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
                //Proxy-Client-IP：apache 服务代理
                ipAddresses = request.getHeader("Proxy-Client-IP");
            }

            if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
                //WL-Proxy-Client-IP：weblogic 服务代理
                ipAddresses = request.getHeader("WL-Proxy-Client-IP");
            }

            if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
                //HTTP_CLIENT_IP：有些代理服务器
                ipAddresses = request.getHeader("HTTP_CLIENT_IP");
            }

            if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
                //X-Real-IP：nginx服务代理
                ipAddresses = request.getHeader("X-Real-IP");
            }

            //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
            if (ipAddresses != null && ipAddresses.length() != 0) {
                ipAddress = ipAddresses.split(",")[0];
            }

            //还是不能获取到，最后再通过request.getRemoteAddr();获取
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
                ipAddress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    /**
     * 调用太平洋网络IP地址查询Web接口（http://whois.pconline.com.cn/），返回ip、地理位置
     */
    public static IpVO getIpVo(String ip) {
        //查本机
        String url = "http://whois.pconline.com.cn/ipJson.jsp?json=true";
        //查指定ip
        if (StrUtil.isNotEmpty(ip)) {
            url = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + ip;
        }

        String read = HttpUtil.get(url, Charset.forName("GBK"));
        return JSONObject.parseObject(read, IpVO.class);
    }

    /**
     * 调用IP地址查询接口（https://ip.dianduidian.com），返回ip、地理位置
     */
    public static IpVO getIpVo() {
        String read = HttpUtil.get("https://ip.dianduidian.com", StandardCharsets.UTF_8);
        return JSONObject.parseObject(read, IpVO.class);
    }
}
