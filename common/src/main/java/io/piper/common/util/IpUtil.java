/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.common.util;

import com.alibaba.fastjson.JSON;
import io.piper.common.pojo.config.IpInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.*;
import java.nio.charset.Charset;
import java.util.Enumeration;

/**
 * IpUtil
 *
 * @author piper
 */
public final class IpUtil {
    private static final Logger log = LoggerFactory.getLogger(IpUtil.class);

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

    public static InetAddress findFirstNonLoopbackAddress() {
        InetAddress result = null;
        try {
            int lowest = Integer.MAX_VALUE;
            for (Enumeration<NetworkInterface> nics = NetworkInterface
                    .getNetworkInterfaces(); nics.hasMoreElements(); ) {
                NetworkInterface ifc = nics.nextElement();
                if (ifc.isUp()) {
                    if (ifc.getIndex() < lowest || result == null) {
                        lowest = ifc.getIndex();
                    } else if (result != null) {
                        continue;
                    }

                    for (Enumeration<InetAddress> addrs = ifc
                            .getInetAddresses(); addrs.hasMoreElements(); ) {
                        InetAddress address = addrs.nextElement();
                        if (address instanceof Inet4Address
                                && !address.isLoopbackAddress()) {
                            result = address;
                        }
                    }
                }
            }
        } catch (IOException ex) {
            log.error("Cannot get first non-loopback address", ex);
        }
        if (result != null) {
            return result;
        }
        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            log.warn("Unable to retrieve localhost");
        }
        return null;
    }

    public static IpInfo getIpVo(String ip) {
        String url = "http://whois.pconline.com.cn/ipJson.jsp?json=true";
        if (StringUtil.isNotEmpty(ip)) {
            url = "http://whois.pconline.com.cn/ipJson.jsp?json=true&ip=" + ip;
        }
        String read = HttpUtil.doGet(url, Charset.forName("GBK"));
        return JSON.parseObject(read, IpInfo.class);
    }

    public static IpInfo getIpVo() {
        return getIpVo(null);
    }

}
