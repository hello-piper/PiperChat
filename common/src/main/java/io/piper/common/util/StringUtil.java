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

/**
 * StringUtil
 *
 * @author piper
 */
public final class StringUtil {
    public static final String EMPTY = "";

    public static boolean isAnyEmpty(Object... objects) {
        if (objects == null) {
            return true;
        }
        for (Object o : objects) {
            if (isEmpty(o)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotEmpty(Object str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(Object str) {
        return str == null || EMPTY.equals(str);
    }

}
