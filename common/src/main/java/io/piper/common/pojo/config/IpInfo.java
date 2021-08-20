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
package io.piper.common.pojo.config;

import lombok.Data;

/**
 * 获取本机IP地址 返回结果
 * 请求地址 https://ip.dianduidian.com/
 *
 * @author piper
 */
@Data
public class IpInfo {

    // IP地址
    private String ip;

    // 运营商
    private String isp;

    // 国家
    private String country;

    // 城市
    private String city;

    // 区
    private String region;
}
