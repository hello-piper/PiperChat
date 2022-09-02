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
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * 文件消息
 *
 * @author piper
 */
@Data
@Message
public class FileMsgBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 文件大小(kb)
     */
    private Long size;

    public FileMsgBody() {
    }

    public FileMsgBody(String fileName, String url, Long size) {
        this.fileName = fileName;
        this.url = url;
        this.size = size;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
