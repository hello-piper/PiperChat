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
package io.piper.common.task;

import com.alibaba.fastjson.JSON;
import io.piper.common.WebSocketUser;
import io.piper.common.constant.Constants;
import io.piper.common.db.RedisDS;
import io.piper.common.pojo.config.AddressInfo;
import io.piper.common.pojo.config.ServerProperties;
import io.piper.common.util.IpUtil;
import io.piper.common.util.ThreadUtil;
import io.piper.common.util.YamlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * ImServerTask
 *
 * @author piper
 */
public class ImServerTask {
    private static final Logger log = LogManager.getLogger(ImServerTask.class);
    private static final AddressInfo ADDRESS_INFO;

    static {
        ServerProperties config = YamlUtil.getConfig("server", ServerProperties.class);
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setIp(IpUtil.getIpVo().getIp());
        addressInfo.setPort(config.getPort());
        addressInfo.setSsl(config.getSsl());
        addressInfo.setWsPath(config.getWsPath());
        ADDRESS_INFO = addressInfo;
    }

    public static void start(String... clazz) {
        try {
            if (null != clazz) {
                for (String clz : clazz) {
                    Class.forName(clz);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ThreadUtil.SCHEDULE_POOL.scheduleWithFixedDelay(() -> {
            ADDRESS_INFO.setOnlineNum(WebSocketUser.onlineNum());
            String info = JSON.toJSONString(ADDRESS_INFO);
            Jedis jedis = RedisDS.getJedis();
            jedis.publish(Constants.CHANNEL_IM_RENEW, info);
            jedis.hset(Constants.IM_SERVER_HASH, ADDRESS_INFO.getIp() + ":" + ADDRESS_INFO.getPort(), info);
            jedis.close();
            log.debug("广播当前网关机 负载信息 >>> {}", info);
        }, 10, 15, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            String info = JSON.toJSONString(ADDRESS_INFO);
            Jedis jedis = RedisDS.getJedis();
            jedis.publish(Constants.CHANNEL_IM_SHUTDOWN, info);
            jedis.hdel(Constants.IM_SERVER_HASH, ADDRESS_INFO.getIp() + ":" + ADDRESS_INFO.getPort());
            jedis.close();
            log.debug("广播当前网关机 关机信息 >>> {}", info);
        }));
    }
}
