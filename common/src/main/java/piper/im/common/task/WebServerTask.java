package piper.im.common.task;

import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.lang.Collections;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import piper.im.common.load_banlance.AddressLoadBalanceHandler;
import piper.im.common.load_banlance.IAddressLoadBalance;
import piper.im.common.pojo.config.AddressInfo;
import piper.im.common.constant.Constants;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;

/**
 * 服务端定时任务
 *
 * @author piper
 */
public class WebServerTask {
    private static final Logger log = LogManager.getLogger(WebServerTask.class);
    private static final IAddressLoadBalance ADDRESS_HANDLER = new AddressLoadBalanceHandler();

    public static void start() {
        // 订阅 消息通道
        new Thread(() -> {
            Jedis jedis = RedisDS.create().getJedis();
            Map<String, String> imServerMap = jedis.hgetAll(Constants.IM_SERVER_HASH);
            if (!Collections.isEmpty(imServerMap)) {
                for (String info : imServerMap.values()) {
                    ADDRESS_HANDLER.flushAddress(JSONObject.parseObject(info, AddressInfo.class));
                }
            }
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    log.info("receiveMessage >>> channel:{} message:{}", channel, message);
                    if (channel.equals(Constants.CHANNEL_IM_RENEW)) {
                        ADDRESS_HANDLER.flushAddress(JSONObject.parseObject(message, AddressInfo.class));
                    } else if (channel.equals(Constants.CHANNEL_IM_SHUTDOWN)) {
                        ADDRESS_HANDLER.removeAddress(JSONObject.parseObject(message, AddressInfo.class));
                    }
                }
            }, Constants.CHANNEL_IM_RENEW, Constants.CHANNEL_IM_SHUTDOWN);
        }, "web-server-task-thread").start();
    }
}
