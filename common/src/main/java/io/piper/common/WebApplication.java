/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.common;

import com.alibaba.fastjson.JSON;
import io.piper.common.constant.Constants;
import io.piper.common.db.RedisDS;
import io.piper.common.load_banlance.AddressLoadBalanceHandler;
import io.piper.common.load_banlance.IAddressLoadBalance;
import io.piper.common.pojo.config.AddressInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;

/**
 * WebServerApplication
 *
 * @author piper
 */
public class WebApplication {
    private static final Logger log = LoggerFactory.getLogger(WebApplication.class);
    private static final IAddressLoadBalance ADDRESS_HANDLER = new AddressLoadBalanceHandler();

    public static void start() {
        new Thread(() -> {
            Jedis jedis = RedisDS.getJedis();
            Map<String, String> serverMap = jedis.hgetAll(Constants.IM_SERVER_HASH);
            if (serverMap != null && !serverMap.isEmpty()) {
                for (String info : serverMap.values()) {
                    ADDRESS_HANDLER.flushAddress(JSON.parseObject(info, AddressInfo.class));
                }
            }

            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    log.debug("onMessage >>> {} {}", channel, message);
                    if (Constants.CHANNEL_IM_RENEW.equals(channel)) {
                        ADDRESS_HANDLER.flushAddress(JSON.parseObject(message, AddressInfo.class));
                    } else if (Constants.CHANNEL_IM_SHUTDOWN.equals(channel)) {
                        ADDRESS_HANDLER.removeAddress(JSON.parseObject(message, AddressInfo.class));
                    }
                }
            }, Constants.CHANNEL_IM_RENEW, Constants.CHANNEL_IM_SHUTDOWN);
        }, "server-task-thread").start();
    }

}
