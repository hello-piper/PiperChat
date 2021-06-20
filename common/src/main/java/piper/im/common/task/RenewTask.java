package piper.im.common.task;

import cn.hutool.db.nosql.redis.RedisDS;
import cn.hutool.http.HttpRequest;
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
 * 定时续约并上报信息
 *
 * @author piper
 */
public class RenewTask {
    private static final Logger log = LogManager.getLogger(RenewTask.class);
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

        // 订阅 需要处理的消息
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                log.info("receiveMessage >>> channel:{} message:{}", channel, message);
            }
        }, "channel:message");

        // 定时续约任务
        new Timer("RenewTimer", true).scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ADDRESS_INFO.setOnlineNum(WebSocketUser.onlineNum());
                String info = JSONObject.toJSONString(ADDRESS_INFO);
                HttpRequest.put(REPORT_URL).body(info).execute();

                log.info("网关机定时汇报信息>>>{}", info);

                // 广播当前网关机 负载信息
                jedis.publish("channel:renew-info", info);
            }
        }, 5000, 10000);

        // 关机回调
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            String info = JSONObject.toJSONString(ADDRESS_INFO);
            HttpRequest.delete(REPORT_URL).body(info).execute();

            log.info("网关机 关机>>>{}", info);

            // 广播当前网关机 关机信息
            jedis.publish("channel:shutdown", info);
        }));
    }
}
