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
package io.piper.common.pojo.req;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;

/**
 * 客户端发送消息
 */
@Data
public class RequestMsg implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 通知类型
     */
    private Integer type;

    /**
     * json数据
     */
    private Map<String, Object> data;

    /**
     * 客户端发送消息类型
     */
    public enum RequestTypeEnum {
        // 进入直播间 data:{roomId:1}
        ENTER_ROOM(1),
        // 退出 data:{roomId:1}
        EXIT_ROOM(2);

        public final Integer type;

        RequestTypeEnum(Integer type) {
            this.type = type;
        }

        public static RequestTypeEnum valueOf(Integer type) {
            return Arrays.stream(RequestTypeEnum.values()).filter(v -> v.type.equals(type)).findFirst().orElse(null);
        }
    }
}
