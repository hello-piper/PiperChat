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
package io.piper.server.spring.config;

import com.alibaba.fastjson.JSON;
import io.piper.common.constant.Constants;
import io.piper.common.load_banlance.AddressLoadBalance;
import io.piper.common.pojo.config.AddressInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@Configuration
public class TaskConfig {
    private static final Logger log = LoggerFactory.getLogger(TaskConfig.class);

    @Resource
    private JedisPool jedisPool;

    @PostConstruct
    public void init() {
        new Thread(() -> {
            Jedis jedis = jedisPool.getResource();
            Map<String, String> imServerMap = jedis.hgetAll(Constants.IM_SERVER_HASH);
            if (imServerMap != null && !imServerMap.isEmpty()) {
                for (String info : imServerMap.values()) {
                    AddressLoadBalance.flushAddress(JSON.parseObject(info, AddressInfo.class));
                }
            }

            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    log.debug("onMessage >>> {} {}", channel, message);
                    if (channel.equals(Constants.CHANNEL_IM_RENEW)) {
                        AddressLoadBalance.flushAddress(JSON.parseObject(message, AddressInfo.class));
                    } else if (channel.equals(Constants.CHANNEL_IM_SHUTDOWN)) {
                        AddressLoadBalance.removeAddress(JSON.parseObject(message, AddressInfo.class));
                    }
                }
            }, Constants.CHANNEL_IM_RENEW, Constants.CHANNEL_IM_SHUTDOWN);
        }, "server-task-thread").start();
    }
}
