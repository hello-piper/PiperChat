package io.piper.common.load_banlance;

import io.piper.common.pojo.config.AddressInfo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public interface IAddressLoadBalance {

    /**
     * 地址信息集合
     */
    ConcurrentHashMap<String, AddressInfo> IM_SERVER_MAP = new ConcurrentHashMap<>();

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
