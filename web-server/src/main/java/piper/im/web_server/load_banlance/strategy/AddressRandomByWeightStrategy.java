package piper.im.web_server.load_banlance.strategy;

import piper.im.common.pojo.AddressInfo;

import java.util.concurrent.ThreadLocalRandom;

public class AddressRandomByWeightStrategy implements IAddressLoadBalanceStrategy {

    @Override
    public AddressInfo getAddress() {
        int size = IM_SERVER_LIST_BY_WEIGHT.size();
        if (size == 0) {
            return null;
        }
        int nextInt = ThreadLocalRandom.current().nextInt(size);
        return IM_SERVER_LIST_BY_WEIGHT.get(nextInt);
    }
}
