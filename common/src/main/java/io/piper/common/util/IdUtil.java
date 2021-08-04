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
package io.piper.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * IdUtil
 *
 * @author piper
 */
public final class IdUtil {
    private static final Logger log = LogManager.getLogger(IdUtil.class);
    private static final Map<String, Object> MAP = new HashMap<>();

    public static Snowflake getSnowflake(String key) {
        if (MAP.containsKey(key)) {
            return (Snowflake) MAP.get(key);
        }
        synchronized (MAP) {
            if (MAP.containsKey(key)) {
                return (Snowflake) MAP.get(key);
            }
            Long workerId = (Long) RedisDS.getJedis().eval("for i=0," + Snowflake.getMaxWorkerId() + " do if redis.call('sadd',KEYS[1],i) == 1 then return i end end",
                    Collections.singletonList(key), Collections.emptyList());
            Snowflake snowflake = new Snowflake(workerId, 0);
            MAP.put(key, snowflake);
            log.debug("get Snowflake workId {}", workerId);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                log.debug("rem Snowflake workId {}", workerId);
                RedisDS.getJedis().srem(key, String.valueOf(workerId));
            }));
            return snowflake;
        }
    }

    public static String fastSimpleUUID() {
        return UUID.randomUUID(false).toString(true);
    }
}
