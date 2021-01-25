package piper.im.web_server;

import com.alibaba.fastjson.JSONObject;
import piper.im.web_server.load_banlance.handler.AddressLoadBalanceHandler;
import piper.im.web_server.load_banlance.handler.IAddressLoadBalanceHandler;
import piper.im.web_server.load_banlance.strategy.AddressRandomByWeightStrategy;
import piper.im.web_server.load_banlance.strategy.IAddressLoadBalanceStrategy;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;

public class AddressServlet extends HttpServlet {

    private final IAddressLoadBalanceHandler addressLoadBalanceHandler = new AddressLoadBalanceHandler();
    private final IAddressLoadBalanceStrategy addressRandomByWeightStrategy = new AddressRandomByWeightStrategy();

    /**
     * 接收IM网关的续约信息
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String line;
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null) {
            stringBuffer.append(line);
        }
        AddressInfo addressInfo = JSONObject.parseObject(stringBuffer.toString(), AddressInfo.class);
        addressLoadBalanceHandler.flushAddress(addressInfo);
    }

    /**
     * 前端获取可用服务地址
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AddressInfo address = addressRandomByWeightStrategy.getAddress();
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        if (!Objects.isNull(address)) {
            writer.println(address.getIp() + ":" + address.getPort() + address.getContainPath());
        }
        writer.flush();
        writer.close();
    }
}
