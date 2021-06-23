package piper.im.common.task;

import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import piper.im.common.WebSocketUser;
import piper.im.common.pojo.config.AddressInfo;
import piper.im.common.pojo.config.ServerConfig;
import piper.im.common.util.IpUtil;
import piper.im.common.util.YamlUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 网关机定时任务
 *
 * @author piper
 */
public class GatewayTask {
    private static final Logger log = LogManager.getLogger(GatewayTask.class);
    private static final String REPORT_URL;
    private static final AddressInfo ADDRESS_INFO;

    static {
        ServerConfig config = YamlUtil.getConfig("server", ServerConfig.class);
        REPORT_URL = config.getReportUrl();
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setIp(IpUtil.getIpVo().getIp());
        addressInfo.setPort(config.getPort());
        addressInfo.setSsl(config.getSsl());
        addressInfo.setHttpPath(config.getHttpPath());
        addressInfo.setWsPath(config.getWsPath());
        ADDRESS_INFO = addressInfo;
    }

    public static void start() {
        Jedis jedis = RedisDS.create().getJedis();

        // 订阅 消息通道
        new Thread(() -> {
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    log.info("receiveMessage >>> channel:{} message:{}", channel, message);
                }
            }, "channel:message");
        }, "gateway-task-thread").start();

        // 定时续约
        new Timer("RenewTimer", true).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ADDRESS_INFO.setOnlineNum(WebSocketUser.onlineNum());
                String info = JSONObject.toJSONString(ADDRESS_INFO);
                jedis.publish("channel:renew-info", info);
                log.info("定时广播当前网关机负载信息 >>> {}", info);
            }
        }, 5000, 10000);

        // 关机回调
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            String info = JSONObject.toJSONString(ADDRESS_INFO);
            jedis.publish("channel:shutdown", info);
            log.info("广播当前网关机 关机信息 >>> {}", info);
        }));
    }
}
