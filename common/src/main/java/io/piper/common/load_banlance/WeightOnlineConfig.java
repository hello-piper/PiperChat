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
package io.piper.common.load_banlance;

/**
 * 权重和连接数对应关系
 * 权重5 online<3W
 * 权重4 3w<online<5w
 * 权重3 5w<online<7w
 * 权重2 7w<online<9w
 * 权重1 online>9w
 */
public class WeightOnlineConfig {

    /**
     * 根据连接数获取权重
     */
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
