/*
 * Copyright 2020 The PiperChat
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
 * Jwt配置类
 *
 * @author piper
 * @date 2021年6月29日 08点00分
 */
@Data
public class JwtProperties {

    /**
     * jwt的秘钥
     */
    private String secret = "7Rv9OTzS";

    /**
     * jwt过期时间(单位:小时)
     */
    private Long expireHour = 24L;

    /**
     * header中标识jwt的字段名称
     */
    private String name = "token";
}
