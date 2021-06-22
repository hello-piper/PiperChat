package piper.im.common.task;

import cn.hutool.db.nosql.redis.RedisDS;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import piper.im.common.load_banlance.AddressLoadBalanceHandler;
import piper.im.common.load_banlance.IAddressLoadBalance;
import piper.im.common.pojo.config.AddressInfo;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * 服务端定时任务
 *
 * @author piper
 */
public class ServerTask {
    private static final Logger log = LogManager.getLogger(ServerTask.class);
    private static final IAddressLoadBalance ADDRESS_HANDLER = new AddressLoadBalanceHandler();

    public static void start() {
        Jedis jedis = RedisDS.create().getJedis();

        // 订阅 消息通道
        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                if (channel.equals("channel:renew-info")) {
                    ADDRESS_HANDLER.flushAddress(JSONObject.parseObject(message, AddressInfo.class));
                } else if (channel.equals("channel:shutdown")) {
                    ADDRESS_HANDLER.removeAddress(JSONObject.parseObject(message, AddressInfo.class));
                } else {
                    log.info("receiveMessage >>> channel:{} message:{}", channel, message);
                }
            }
        }, "channel:renew-info", "channel:shutdown");
    }
}
