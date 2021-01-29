package piper.im.common.task;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import piper.im.common.WebSocketUser;
import piper.im.common.pojo.AddressInfo;
import piper.im.common.pojo.MessageServerConfig;
import piper.im.common.util.IpUtil;
import piper.im.common.util.YamlUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
        MessageServerConfig config = YamlUtil.getConfig("server", MessageServerConfig.class);
        REPORT_URL = config.getReportUrl();
        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setIp(IpUtil.getIpVo(null).getIp());
        addressInfo.setPort(config.getPort());
        addressInfo.setSsl(config.getSsl());
        addressInfo.setHttpPath(config.getHttpPath());
        addressInfo.setWsPath(config.getWsPath());
        ADDRESS_INFO = addressInfo;
    }

    public static void start() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(() -> {
            ADDRESS_INFO.setOnlineNum(WebSocketUser.onlineNum());
            String info = JSONObject.toJSONString(ADDRESS_INFO);
            HttpRequest.put(REPORT_URL).body(info).execute();
            log.info("网关机定时汇报信息>>>{}", info);
        }, 1, 10, TimeUnit.SECONDS);
    }

}
