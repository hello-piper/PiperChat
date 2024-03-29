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
 * VideoMsgBody
 *
 * @author piper
 */
@Data
public class VideoMsgBody implements Msg.IMsgBody, Serializable {
    private static final long serialVersionUID = 1;

    private String name;

    private String url;

    private Integer seconds;

    private Integer size;

    public VideoMsgBody() {
    }

    public VideoMsgBody(String name, String url, Integer seconds, Integer size) {
        this.name = name;
        this.url = url;
        this.seconds = seconds;
        this.size = size;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
