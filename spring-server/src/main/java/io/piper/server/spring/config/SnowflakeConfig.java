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

import io.piper.common.constant.Constants;
import io.piper.common.util.Snowflake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;

@Configuration
public class SnowflakeConfig {
    private static final Logger log = LoggerFactory.getLogger(SnowflakeConfig.class);

    @Resource
    private JedisPool jedisPool;

    @Bean
    public Snowflake getSnowflake() {
        Long workerId = (Long) jedisPool.getResource().eval("for i=0," + Snowflake.getMaxWorkerId() + " do if redis.call('sadd',KEYS[1],i) == 1 then return i end end",
                Collections.singletonList(Constants.IM_WORK_ID), Collections.emptyList());
        log.debug("get Snowflake workId {}", workerId);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.debug("rem Snowflake workId {}", workerId);
            jedisPool.getResource().srem(Constants.IM_WORK_ID, String.valueOf(workerId));
        }));
        return new Snowflake(workerId, 0);
    }
}
