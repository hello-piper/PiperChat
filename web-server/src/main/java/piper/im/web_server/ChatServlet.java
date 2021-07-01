package piper.im.web_server;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONObject;
import piper.im.common.constant.Constants;
import piper.im.common.load_banlance.AddressLoadBalanceHandler;
import piper.im.common.load_banlance.IAddressLoadBalance;
import piper.im.common.util.RedisDS;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChatServlet extends HttpServlet {
    private final IAddressLoadBalance addressHandler = new AddressLoadBalanceHandler();

    /**
     * 前端获取可用服务地址
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data = JSONObject.toJSONString(addressHandler.getAddressByWeight());
        IoUtil.writeUtf8(resp.getOutputStream(), true, data);
    }

    /**
     * 接收用户发送消息
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String chatMsg = IoUtil.read(req.getReader());
        Jedis jedis = RedisDS.getJedis();
        jedis.publish(Constants.CHANNEL_IM_MESSAGE, chatMsg);
        jedis.close();
    }
}
