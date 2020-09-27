package piper.im.address.load_banlance.handler;

import piper.im.address.AddressInfo;
import piper.im.address.load_banlance.IAddressLoadBalance;

public interface IAddressLoadBalanceHandler extends IAddressLoadBalance {

    void flushAddress(AddressInfo addressInfo);
}
