package piper.im.web_server.load_banlance.handler;

import piper.im.common.pojo.AddressInfo;
import piper.im.web_server.WeightOnlineConfig;

import java.util.ArrayList;
import java.util.Objects;

public class AddressLoadBalanceHandler implements IAddressLoadBalanceHandler {

    @Override
    public void flushAddress(AddressInfo addressInfo) {
        AddressInfo info = ADDRESS_MAP.get(addressInfo.getIp());
        ADDRESS_MAP.put(addressInfo.getIp(), addressInfo);
        if (Objects.isNull(info)) {
            int weight = WeightOnlineConfig.getWeightBYOnline(addressInfo.getOnlineNum());
            ArrayList<AddressInfo> list = new ArrayList<>();
            for (int i = 0; i < weight; i++) {
                list.add(addressInfo);
            }
            ADDRESS_LIST_BY_WEIGHT.addAll(list);
        } else {
            int oldWeight = WeightOnlineConfig.getWeightBYOnline(info.getOnlineNum());
            int weight = WeightOnlineConfig.getWeightBYOnline(addressInfo.getOnlineNum());
            if (oldWeight != weight) {
                for (int i = 0; i < Math.abs(weight - oldWeight); i++) {
                    if (weight > oldWeight) {
                        ADDRESS_LIST_BY_WEIGHT.add(addressInfo);
                    } else {
                        ADDRESS_LIST_BY_WEIGHT.remove(addressInfo);
                    }
                }
            }
        }
    }

}
