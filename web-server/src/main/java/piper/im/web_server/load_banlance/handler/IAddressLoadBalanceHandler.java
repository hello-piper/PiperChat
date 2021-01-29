package piper.im.web_server.load_banlance.handler;

import piper.im.common.pojo.AddressInfo;
import piper.im.web_server.load_banlance.IAddressLoadBalance;

public interface IAddressLoadBalanceHandler extends IAddressLoadBalance {

    void flushAddress(AddressInfo addressInfo);
}
