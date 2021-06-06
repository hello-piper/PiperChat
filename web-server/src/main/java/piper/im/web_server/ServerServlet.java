package piper.im.web_server;

import cn.hutool.core.io.IoUtil;

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
        ServerHandler.ServerHandlerHolder.get().sendMsg(data);
    }
}
