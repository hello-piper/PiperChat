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

import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.lang.Collections;
import io.piper.common.constant.Constants;
import io.piper.common.pojo.config.AddressInfo;
import io.piper.server.spring.service.ChatService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;
import java.util.Map;

@Log4j2
@Configuration
public class TaskConfig {

    @Autowired
    private JedisPool jedisPool;

    @PostConstruct
    public void init() {
        new Thread(() -> {
            Jedis jedis = jedisPool.getResource();
            Map<String, String> imServerMap = jedis.hgetAll(Constants.IM_SERVER_HASH);
            if (!Collections.isEmpty(imServerMap)) {
                for (String info : imServerMap.values()) {
                    ChatService.ADDRESS_HANDLER.flushAddress(JSONUtil.toBean(info, AddressInfo.class));
                }
            }
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    log.info("onMessage >>> {} {}", channel, message);
                    if (channel.equals(Constants.CHANNEL_IM_RENEW)) {
                        ChatService.ADDRESS_HANDLER.flushAddress(JSONUtil.toBean(message, AddressInfo.class));
                    } else if (channel.equals(Constants.CHANNEL_IM_SHUTDOWN)) {
                        ChatService.ADDRESS_HANDLER.removeAddress(JSONUtil.toBean(message, AddressInfo.class));
                    }
                }
            }, Constants.CHANNEL_IM_RENEW, Constants.CHANNEL_IM_SHUTDOWN);
        }, "server-task-thread").start();
    }
}
