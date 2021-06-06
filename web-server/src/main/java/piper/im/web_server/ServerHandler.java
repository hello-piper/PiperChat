package piper.im.web_server;

import cn.hutool.http.HttpUtil;
import piper.im.web_server.load_banlance.IAddressLoadBalance;

public class ServerHandler {

    public static class ServerHandlerHolder {
        private static ServerHandler serverHandler;

        public static ServerHandler get() {
            return serverHandler;
        }
    }

    /**
     * 用户发送消息
     */
    public void sendMsg(String data) {
        IAddressLoadBalance.IM_SERVER_LIST_BY_WEIGHT.forEach(server -> HttpUtil.post(server.getHttpPath(), data));
    }
}
