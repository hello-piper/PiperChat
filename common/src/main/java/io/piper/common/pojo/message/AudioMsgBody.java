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

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

import lombok.Data;

/**
 * 语言消息
 * @author piper
 */
@Data
public class AudioMsgBody implements Msg.IMsgBody, Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 语言地址
     */
    private String url;

    /**
     * 语言时长(s)
     */
    private Integer seconds;

    /**
     * 语言大小(kb)
     */
    private Integer size;

    public AudioMsgBody() {
    }

    public AudioMsgBody(String url, Integer seconds, Integer size) {
        this.url = url;
        this.seconds = seconds;
        this.size = size;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
