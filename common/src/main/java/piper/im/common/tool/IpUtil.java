package piper.im.common.tool;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtil {

    private static final Pattern IP_PATTERN = Pattern.compile("\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}");

    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println("本机名：" + address.getHostName());
        System.out.println("本机IP地址：" + address.getHostAddress());
        System.out.println(getLocalIpByLuTool());
        System.out.println(getLocalIp());
    }

    public static String getLocalIpByLuTool() {
        String result = HttpUtil.get("https://ip.tool.lu/");
        Matcher matches = IP_PATTERN.matcher(result);
        if (matches.find()) {
            return matches.group();
        }
        return null;
    }

    public static String getLocalIp() {
        String result = HttpUtil.get("https://www.ip.cn/api/index?ip=&type=0");
        IPCN ipcn = JSONObject.parseObject(result, IPCN.class);
        return ipcn.getIp();
    }

}

class IPCN {
    private int code;
    private String ip;
    private String address;
    private Boolean isDomain;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getDomain() {
        return isDomain;
    }

    public void setDomain(Boolean domain) {
        isDomain = domain;
    }
}