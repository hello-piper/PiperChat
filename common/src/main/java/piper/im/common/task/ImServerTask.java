package piper.im.common.task;

import cn.hutool.json.JSONUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import piper.im.common.WebSocketUser;
import piper.im.common.constant.Constants;
import piper.im.common.pojo.config.AddressInfo;
import piper.im.common.pojo.config.ServerConfig;
import piper.im.common.util.IpUtil;
import piper.im.common.util.RedisDS;
import piper.im.common.util.ThreadUtil;
import piper.im.common.util.YamlUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.TimeUnit;

/**
 * 网关机定时任务
 *
 * @author piper
 */
public class ImServerTask {
    private static final Logger log = LogManager.getLogger(ImServerTask.class);
    private static final AddressInfo ADDRESS_INFO;

    static {
        ServerConfig config = YamlUtil.getConfig("server", ServerConfig.class);
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setIp(IpUtil.getIpVo().getIp());
        addressInfo.setPort(config.getPort());
        addressInfo.setSsl(config.getSsl());
        addressInfo.setWsPath(config.getWsPath());
        ADDRESS_INFO = addressInfo;
    }

    public static void start() {
        // 订阅 消息通道
        new Thread(() -> {
            RedisDS.getJedis().subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    log.info("receiveMessage >>> channel:{} message:{}", channel, message);
                }
            }, Constants.CHANNEL_IM_MESSAGE);
        }, "im-server-task-thread").start();

        // 定时续约
        ThreadUtil.SCHEDULE_POOL.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                ADDRESS_INFO.setOnlineNum(WebSocketUser.onlineNum());
                String info = JSONUtil.toJsonStr(ADDRESS_INFO);
                Jedis jedis = RedisDS.getJedis();
                jedis.publish(Constants.CHANNEL_IM_RENEW, info);
                jedis.hset(Constants.IM_SERVER_HASH, ADDRESS_INFO.getIp() + ":" + ADDRESS_INFO.getPort(), info);
                jedis.close();
                log.info("广播当前网关机 负载信息 >>> {}", info);
            }
        }, 10, 15, TimeUnit.SECONDS);

        // 关机回调
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            String info = JSONUtil.toJsonStr(ADDRESS_INFO);
            Jedis jedis = RedisDS.getJedis();
            jedis.publish(Constants.CHANNEL_IM_SHUTDOWN, info);
            jedis.hdel(Constants.IM_SERVER_HASH, ADDRESS_INFO.getIp() + ":" + ADDRESS_INFO.getPort());
            jedis.close();
            log.info("广播当前网关机 关机信息 >>> {}", info);
        }));
    }
}
