package io.piper.common.load_banlance;

import io.piper.common.pojo.config.AddressInfo;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class AddressLoadBalanceHandler implements IAddressLoadBalance {

    @Override
    public void flushAddress(AddressInfo addressInfo) {
        String key = addressInfo.getIp() + ":" + addressInfo.getPort();
        AddressInfo info = IM_SERVER_MAP.get(key);
        IM_SERVER_MAP.put(key, addressInfo);
        int weight = WeightOnlineConfig.getWeightBYOnline(addressInfo.getOnlineNum());
        if (Objects.isNull(info)) {
            for (int i = 0; i < weight; i++) {
                IM_SERVER_LIST_BY_WEIGHT.add(addressInfo);
            }
        } else {
            int oldWeight = WeightOnlineConfig.getWeightBYOnline(info.getOnlineNum());
            if (oldWeight == weight) {
                return;
            }
            for (int i = 0; i < Math.abs(weight - oldWeight); i++) {
                if (weight > oldWeight) {
                    IM_SERVER_LIST_BY_WEIGHT.add(addressInfo);
                } else {
                    IM_SERVER_LIST_BY_WEIGHT.remove(addressInfo);
                }
            }
        }
    }

    @Override
    public void removeAddress(AddressInfo addressInfo) {
        String key = addressInfo.getIp() + ":" + addressInfo.getPort();
        AddressInfo info = IM_SERVER_MAP.remove(key);
        if (!Objects.isNull(info)) {
            int oldWeight = WeightOnlineConfig.getWeightBYOnline(info.getOnlineNum());
            for (int i = 0; i < oldWeight; i++) {
                IM_SERVER_LIST_BY_WEIGHT.remove(addressInfo);
            }
        }
    }

    @Override
    public AddressInfo getAddressByWeight() {
        int size = IM_SERVER_LIST_BY_WEIGHT.size();
        if (size == 0) {
            return null;
        }
        int nextInt = ThreadLocalRandom.current().nextInt(size);
        return IM_SERVER_LIST_BY_WEIGHT.get(nextInt);
    }
}
