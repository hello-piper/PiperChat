package piper.im.address.load_banlance.strategy;

import piper.im.address.AddressInfo;
import piper.im.address.load_banlance.IAddressLoadBalance;

public interface IAddressLoadBalanceStrategy extends IAddressLoadBalance {

    AddressInfo getAddress();
}
