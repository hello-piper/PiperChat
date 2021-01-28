package piper.im.web_server.load_banlance;

import piper.im.common.AddressInfo;

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
