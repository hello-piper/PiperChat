package piper.im.common.util;

import piper.im.common.pojo.config.RedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDS {
    private static final JedisPool JEDIS_POOL;

    static {
        RedisConfig config = YamlUtil.getConfig("redis", RedisConfig.class);
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(config.getMaxIdle());
        jedisPoolConfig.setMinIdle(config.getMinIdle());
        jedisPoolConfig.setMaxTotal(config.getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(config.getMaxWait());
        JEDIS_POOL = new JedisPool(jedisPoolConfig, config.getHost(), config.getPort(), config.getTimeout(), config.getPassword());
    }

    public static Jedis getJedis() {
        return JEDIS_POOL.getResource();
    }
}