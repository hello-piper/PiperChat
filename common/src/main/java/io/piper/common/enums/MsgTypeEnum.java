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
 * @author piper
 */
public enum MsgTypeEnum {
    // 文本消息
    TEXT(0),
    // 图片
    IMAGE(1),
    // 语音
    AUDIO(2),
    // 视频
    VIDEO(3),
    // 文件
    FILE(4),
    // 地理位置
    LOCATION(5),
    // 信令消息
    CMD(6),
    // 自定义消息
    CUSTOM(7);

    public final Integer type;

    MsgTypeEnum(Integer type) {
        this.type = type;
    }

    public static MsgTypeEnum valueOf(Integer type) {
        return Arrays.stream(values()).filter(v -> v.type.equals(type)).findFirst().orElse(null);
    }
}
