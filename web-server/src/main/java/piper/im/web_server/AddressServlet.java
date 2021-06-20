package piper.im.web_server;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONObject;
import piper.im.common.pojo.config.AddressInfo;
import piper.im.web_server.load_banlance.AddressLoadBalanceHandler;
import piper.im.web_server.load_banlance.IAddressLoadBalance;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddressServlet extends HttpServlet {
    private final IAddressLoadBalance addressHandler = new AddressLoadBalanceHandler();

    /**
     * 接收IM网关的续约信息
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data = IoUtil.read(req.getReader());
        addressHandler.flushAddress(JSONObject.parseObject(data, AddressInfo.class));
    }

    /**
     * 接收IM网关的移除信息
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data = IoUtil.read(req.getReader());
        addressHandler.removeAddress(JSONObject.parseObject(data, AddressInfo.class));
    }

    /**
     * 前端获取可用服务地址
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        IoUtil.writeUtf8(resp.getOutputStream(), true, JSONObject.toJSONString(addressHandler.getAddressByWeight()));
    }
}
