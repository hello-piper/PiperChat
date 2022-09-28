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
package io.piper.common.constant;

/**
 * 客户端header参数clientName匹配枚举元素的描述值(desc)
 *
 * @author piper
 */
public enum ClientNameEnum {
    /**
     * 台式机浏览器
     */
    WEB(1, "web"),

    /**
     * m移动端浏览器
     */
    M(2, "m"),

    /**
     * h5移动端浏览器
     */
    H5(3, "h5"),

    /**
     * Android
     */
    ANDROID(4, "android"),

    /**
     * iOS
     */
    IOS(5, "ios"),

    /**
     * 台式机应用程序
     */
    OBS(6, "obs"),

    /**
     * 推流助手
     */
    MOYUN(7, "moyun"),

    /**
     * Android 助手
     */
    ANDROID_ASSISTANT(8, "android-assistant"),

    /**
     * iOS 助手
     */
    IOS_ASSISTANT(9, "ios-assistant"),

    /**
     * WEB PWA
     */
    WEB_APP(10, "webapp");


    private final int code;
    private final String name;

    ClientNameEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return this.code;
    }

    public static ClientNameEnum findByName(String name) {
        for (ClientNameEnum ce : ClientNameEnum.values()) {
            if (ce.name.equalsIgnoreCase(name)) {
                return ce;
            }
        }
        return null;
    }

}
