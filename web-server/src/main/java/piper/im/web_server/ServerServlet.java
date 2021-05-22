package piper.im.web_server;

import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpUtil;
import piper.im.web_server.load_banlance.IAddressLoadBalance;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServerServlet extends HttpServlet {

    /**
     * 接收用户发送消息
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data = IoUtil.read(req.getReader());
        IAddressLoadBalance.IM_SERVER_LIST_BY_WEIGHT.forEach(server -> HttpUtil.post(server.getHttpPath(), data));
    }
}
