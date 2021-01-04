package piper.im.web_server.load_banlance.strategy;

import piper.im.web_server.AddressInfo;

import java.util.concurrent.ThreadLocalRandom;

public class AddressRandomByWeightStrategy implements IAddressLoadBalanceStrategy {

    @Override
    public AddressInfo getAddress() {
        int size = ADDRESS_LIST_BY_WEIGHT.size();
        if (size == 0) {
            return null;
        }
        int nextInt = ThreadLocalRandom.current().nextInt(size);
        return ADDRESS_LIST_BY_WEIGHT.get(nextInt);
    }
}
