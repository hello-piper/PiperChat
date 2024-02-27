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
 * LocationMsgBody
 *
 * @author piper
 */
@Data
public class LocationMsgBody implements Msg.IMsgBody, Serializable {
    private static final long serialVersionUID = 1;

    public String address;

    public Double latitude;

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
        return JSON.toJSONString(this);
    }
}
