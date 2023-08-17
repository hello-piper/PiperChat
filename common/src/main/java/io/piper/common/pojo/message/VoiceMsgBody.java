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
package io.piper.common.pojo.message;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;

/**
 * 语言消息
 *
 * @author piper
 */
@Data
public class VoiceMsgBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 语言地址
     */
    private String url;

    /**
     * 语言时长(s)
     */
    private Integer length;

    /**
     * 语言大小(kb)
     */
    private Long size;

    public VoiceMsgBody() {
    }

    public VoiceMsgBody(String url, Integer length, Long size) {
        this.url = url;
        this.length = length;
        this.size = size;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
