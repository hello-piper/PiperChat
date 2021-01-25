package piper.im.web_server;

import cn.hutool.http.HttpUtil;
import piper.im.web_server.load_banlance.IAddressLoadBalance;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

public class ServerServlet extends HttpServlet {

    /**
     * 接收用户发送消息
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String line;
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = req.getReader();
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        IAddressLoadBalance.ADDRESS_LIST_BY_WEIGHT.forEach(address -> {
            HttpUtil.post(address.getIp() + ":" + address.getPort() + "/" + address.getContainPath(), builder.toString());
        });
    }
}
