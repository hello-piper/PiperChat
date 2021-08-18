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
package io.piper.common.enums;

import java.util.Arrays;

/**
 * 消息类型枚举
 *
 * @author piper
 */
public enum MsgTypeEnum {
    // 文本消息
    TEXT((byte) 0),
    // 图片
    IMAGE((byte) 1),
    // 语音
    VOICE((byte) 2),
    // 视频
    VIDEO((byte) 3),
    // 文件
    FILE((byte) 4),
    // 地理位置
    LOCATION((byte) 5),
    // 信令
    CMD((byte) 6);

    public Byte type;

    MsgTypeEnum(Byte type) {
        this.type = type;
    }

    public static MsgTypeEnum valueOf(Byte type) {
        return Arrays.stream(values()).filter(v -> v.type.equals(type)).findFirst().orElse(null);
    }
}
