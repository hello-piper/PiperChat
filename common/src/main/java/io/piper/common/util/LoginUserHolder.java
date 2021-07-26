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
package io.piper.common.util;

import io.piper.common.pojo.dto.UserTokenDTO;

public class LoginUserHolder {
    private static final ThreadLocal<UserTokenDTO> holder = new ThreadLocal<>();

    public static void put(UserTokenDTO dto) {
        holder.set(dto);
    }

    public static UserTokenDTO get() {
        return holder.get();
    }

    public static void remove() {
        holder.remove();
    }
}