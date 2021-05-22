package piper.im.web_server;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONObject;
import piper.im.common.pojo.AddressInfo;
import piper.im.web_server.load_banlance.handler.AddressLoadBalanceHandler;
import piper.im.web_server.load_banlance.handler.IAddressLoadBalanceHandler;
import piper.im.web_server.load_banlance.strategy.AddressRandomByWeightStrategy;
import piper.im.web_server.load_banlance.strategy.IAddressLoadBalanceStrategy;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddressServlet extends HttpServlet {
    private final IAddressLoadBalanceHandler addressLoadBalanceHandler = new AddressLoadBalanceHandler();
    private final IAddressLoadBalanceStrategy addressRandomByWeightStrategy = new AddressRandomByWeightStrategy();

    /**
     * 接收IM网关的续约信息
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data = IoUtil.read(req.getReader());
        AddressInfo addressInfo = JSONObject.parseObject(data, AddressInfo.class);
        addressLoadBalanceHandler.flushAddress(addressInfo);
    }

    /**
     * 前端获取可用服务地址
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AddressInfo address = addressRandomByWeightStrategy.getAddress();
        IoUtil.writeUtf8(resp.getOutputStream(), true, JSONObject.toJSONString(address));
    }
}
