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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * IAddressLoadBalance
 *
 * @author piper
 */
public interface IAddressLoadBalance {

    /**
     * 地址信息集合
     */
    Map<String, AddressInfo> IM_SERVER_MAP = new ConcurrentHashMap<>();

    /**
     * 根据权重进行分布
     */
    CopyOnWriteArrayList<AddressInfo> IM_SERVER_LIST_BY_WEIGHT = new CopyOnWriteArrayList<>();

    /**
     * IM网关上报当前信息
     */
    void flushAddress(AddressInfo addressInfo);

    /**
     * IM网关机 移除
     */
    void removeAddress(AddressInfo addressInfo);

    /**
     * 获取一个网关机 加权随机
     */
    AddressInfo getAddressByWeight();
}
