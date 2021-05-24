package piper.im.web_server.load_banlance.handler;

import piper.im.common.pojo.AddressInfo;
import piper.im.web_server.load_banlance.IAddressLoadBalance;

public interface IAddressLoadBalanceHandler extends IAddressLoadBalance {

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
