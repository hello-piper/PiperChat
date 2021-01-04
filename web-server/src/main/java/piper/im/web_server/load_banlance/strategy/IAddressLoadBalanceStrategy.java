package piper.im.web_server.load_banlance.strategy;

import piper.im.web_server.AddressInfo;
import piper.im.web_server.load_banlance.IAddressLoadBalance;

public interface IAddressLoadBalanceStrategy extends IAddressLoadBalance {

    AddressInfo getAddress();
}
