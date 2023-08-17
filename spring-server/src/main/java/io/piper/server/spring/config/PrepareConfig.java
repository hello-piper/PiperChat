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
import io.piper.common.util.Snowflake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.Map;

@Configuration
public class PrepareConfig {
    private static final Logger log = LoggerFactory.getLogger(PrepareConfig.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    Long workerId;

    @PostConstruct
    public void init() {
        Map<Object, Object> imServerMap = redisTemplate.opsForHash().entries(Constants.IM_SERVER_HASH);
        for (Object info : imServerMap.values()) {
            AddressLoadBalance.flushAddress(JSON.parseObject(JSON.toJSONString(info), AddressInfo.class));
        }

        redisTemplate.getRequiredConnectionFactory().getConnection().subscribe((message, pattern) -> {
            String body = new String(message.getBody());
            String channel = new String(message.getChannel());
            log.debug("onMessage >>> {} {}", channel, body);
            if (channel.equals(Constants.CHANNEL_IM_RENEW)) {
                AddressLoadBalance.flushAddress(JSON.parseObject(body, AddressInfo.class));
            } else if (channel.equals(Constants.CHANNEL_IM_SHUTDOWN)) {
                AddressLoadBalance.removeAddress(JSON.parseObject(body, AddressInfo.class));
            }
        }, Constants.CHANNEL_IM_RENEW.getBytes(), Constants.CHANNEL_IM_SHUTDOWN.getBytes());

        workerId = redisTemplate.execute(
                new DefaultRedisScript<>("for i=0," + Snowflake.getMaxWorkerId() + " do if redis.call('sadd',KEYS[1],i) == 1 then return i end end", Long.class),
                Collections.singletonList(Constants.IM_WORK_ID), Collections.emptyList());
        log.debug("get Snowflake workId {}", workerId);
    }

    @Bean
    public Snowflake getSnowflake() {
        return new Snowflake(workerId, 0);
    }

    @PreDestroy
    public void destroy() {
        redisTemplate.opsForSet().remove(Constants.IM_WORK_ID, String.valueOf(workerId));
        log.debug("rem Snowflake workId {}", workerId);
    }

}
