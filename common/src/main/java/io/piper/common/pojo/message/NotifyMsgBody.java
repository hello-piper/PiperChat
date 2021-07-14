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
import java.util.Map;

/**
 * 通知消息
 *
 * @author piper
 */
@Data
@Message
public class NotifyMsgBody implements Serializable {
    private static final long serialVersionUID = 1;

    /**
     * 通知类型
     */
    private String type;

    /**
     * 参数
     */
    private Map<String, String> params;

    public NotifyMsgBody() {
    }

    public NotifyMsgBody(String type, Map<String, String> params) {
        this.type = type;
        this.params = params;
    }

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
