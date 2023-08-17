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
package io.piper.common.db;

import io.piper.common.pojo.config.RedisProperties;
import io.piper.common.util.StringUtil;
import io.piper.common.util.YamlUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * RedisDS
 *
 * @author piper
 */
public class RedisDS {
    private static JedisPool JEDIS_POOL = null;

    static {
        RedisProperties config = YamlUtil.getConfig("redis", RedisProperties.class);
        if (config != null) {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMinIdle(config.getMinIdle());
            jedisPoolConfig.setMaxIdle(config.getMaxIdle());
            jedisPoolConfig.setMaxTotal(config.getMaxActive());
            jedisPoolConfig.setMaxWait(Duration.ofMillis(config.getMaxWait()));
            if (StringUtil.isEmpty(config.getPassword())) {
                JEDIS_POOL = new JedisPool(config.getHost(), config.getPort());
            } else {
                JEDIS_POOL = new JedisPool(jedisPoolConfig, config.getHost(), config.getPort(), config.getTimeout(), config.getPassword());
            }
        }
    }

    public static void consumer(JedisConsumer consumer) {
        try (Jedis jedis = JEDIS_POOL.getResource()) {
            consumer.consumer(jedis);
        }
    }

    public static <T> T execute(JedisExecutor<T> executor) {
        try (Jedis jedis = JEDIS_POOL.getResource()) {
            return executor.execute(jedis);
        }
    }
}
