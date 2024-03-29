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

import io.piper.common.pojo.config.AddressInfo;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * AddressLoadBalance
 *
 * @author piper
 */
public class AddressLoadBalance {

    static final Map<String, AddressInfo> IM_SERVER_MAP = new ConcurrentHashMap<>();

    static final CopyOnWriteArrayList<AddressInfo> IM_SERVER_LIST_BY_WEIGHT = new CopyOnWriteArrayList<>();

    public static void flushAddress(AddressInfo addressInfo) {
        String key = addressInfo.getIp() + ":" + addressInfo.getPort();
        AddressInfo info = IM_SERVER_MAP.get(key);
        IM_SERVER_MAP.put(key, addressInfo);
        int weight = WeightOnlineConfig.getWeightBYOnline(addressInfo.getOnlineNum());
        if (Objects.isNull(info)) {
            for (int i = 0; i < weight; i++) {
                IM_SERVER_LIST_BY_WEIGHT.add(addressInfo);
            }
        } else {
            int oldWeight = WeightOnlineConfig.getWeightBYOnline(info.getOnlineNum());
            if (oldWeight == weight) {
                return;
            }
            for (int i = 0; i < Math.abs(weight - oldWeight); i++) {
                if (weight > oldWeight) {
                    IM_SERVER_LIST_BY_WEIGHT.add(addressInfo);
                } else {
                    IM_SERVER_LIST_BY_WEIGHT.remove(addressInfo);
                }
            }
        }
    }

    public static void removeAddress(AddressInfo addressInfo) {
        String key = addressInfo.getIp() + ":" + addressInfo.getPort();
        AddressInfo info = IM_SERVER_MAP.remove(key);
        if (!Objects.isNull(info)) {
            int oldWeight = WeightOnlineConfig.getWeightBYOnline(info.getOnlineNum());
            for (int i = 0; i < oldWeight; i++) {
                IM_SERVER_LIST_BY_WEIGHT.remove(addressInfo);
            }
        }
    }

    public static AddressInfo getAddressByWeight() {
        int size = IM_SERVER_LIST_BY_WEIGHT.size();
        if (size == 0) {
            return null;
        }
        int nextInt = ThreadLocalRandom.current().nextInt(size);
        return IM_SERVER_LIST_BY_WEIGHT.get(nextInt);
    }
}
