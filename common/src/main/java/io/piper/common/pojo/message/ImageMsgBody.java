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

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * 图片消息
 *
 * @author piper
 */
@Data
@Message
public class ImageMsgBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 图片宽度
     */
    private Double width;

    /**
     * 图片高度
     */
    private Double height;

    /**
     * 图片大小(kb)
     */
    private Long size;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     * 图片缩略图地址
     */
    private String thumbnailUrl;

    public ImageMsgBody() {
    }

    public ImageMsgBody(Double width, Double height, Long size, String imgUrl, String thumbnailUrl) {
        this.width = width;
        this.height = height;
        this.size = size;
        this.imgUrl = imgUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
