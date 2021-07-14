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
package io.piper.common.enums;

import java.util.Arrays;

/**
 * 聊天类型枚举
 *
 * @author piper
 */
public enum ChatTypeEnum {
    // 单聊
    SINGLE((byte) 0),
    // 群聊
    GROUP((byte) 1),
    // 聊天室
    CHATROOM((byte) 2);

    public Byte type;

    ChatTypeEnum(Byte type) {
        this.type = type;
    }

    public static ChatTypeEnum valueOf(Byte type) {
        return Arrays.stream(values()).filter(v -> v.type.equals(type)).findFirst().orElse(null);
    }
}
