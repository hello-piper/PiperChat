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
 * ImageMsgBody
 *
 * @author piper
 */
@Data
public class ImageMsgBody implements Msg.IMsgBody, Serializable {
    private static final long serialVersionUID = 1;

    private Integer width;

    private Integer height;

    private Integer size;

    private String url;

    public ImageMsgBody() {
    }

    public ImageMsgBody(Integer width, Integer height, Integer size, String url, String thumbnailUrl) {
        this.width = width;
        this.height = height;
        this.size = size;
        this.url = url;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
