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
package io.piper.common.pojo.message;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * 位置消息
 *
 * @author piper
 */
@Data
@Message
public class LocationMsgBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 地址
     */
    public String address;

    /**
     * 维度
     */
    public Double latitude;

    /**
     * 经度
     */
    public Double longitude;

    public LocationMsgBody() {
    }

    public LocationMsgBody(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
