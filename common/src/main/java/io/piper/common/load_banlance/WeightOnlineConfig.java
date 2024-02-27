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
package io.piper.common.load_banlance;

/**
 * WeightOnlineConfig
 *
 * @author piper
 */
public class WeightOnlineConfig {

    public static int getWeightBYOnline(Integer online) {
        if (online < 30000) {
            return 5;
        }
        if (online < 50000) {
            return 4;
        }
        if (online < 70000) {
            return 3;
        }
        if (online < 90000) {
            return 2;
        }
        return 1;
    }
}
