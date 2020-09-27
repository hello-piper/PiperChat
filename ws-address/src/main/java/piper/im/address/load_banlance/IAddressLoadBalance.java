package piper.im.address.load_banlance;

import piper.im.address.AddressInfo;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public interface IAddressLoadBalance {

    /**
     * 地址信息集合
     */
    ConcurrentHashMap<String, AddressInfo> ADDRESS_MAP = new ConcurrentHashMap<>();

    /**
     * 根据权重进行分布
     */
    CopyOnWriteArrayList<AddressInfo> ADDRESS_LIST_BY_WEIGHT = new CopyOnWriteArrayList<>();
}
