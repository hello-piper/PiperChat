package io.piper.im.undertow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.piper.common.WebSocketUser;
import io.piper.common.pojo.message.Msg;
import io.piper.im.undertow.coder.JsonDecode;
import io.piper.im.undertow.coder.JsonEncode;
import io.piper.im.undertow.coder.ProtostuffDecode;
import io.piper.im.undertow.coder.ProtostuffEncode;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@ServerEndpoint(value = "/websocket", encoders = {JsonEncode.class, ProtostuffEncode.class}, decoders = {JsonDecode.class, ProtostuffDecode.class})
public class WebSocketEndpoint {

    private final Logger log = LogManager.getLogger(WebSocketEndpoint.class);

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        session.setMaxIdleTimeout(10000);
        WebSocketUser.put(session.getId(), this);
        log.debug("有新连接加入！当前在线人数为 {}", WebSocketUser.onlineNum());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        WebSocketUser.remove(session.getId());
        log.debug("有一连接关闭！当前在线人数为 {}", WebSocketUser.onlineNum());
    }

    @OnMessage
    public void message(PongMessage message, Session session) throws IOException {
        byte[] chars = "pong".getBytes(StandardCharsets.UTF_8);
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate = allocate.put(chars);
        session.getAsyncRemote().sendPong(allocate);
    }

    @OnMessage
    public void message(Msg msg, Session session) throws IOException, EncodeException {
        log.debug("来自客户端的消息 {}", msg);
        this.session.getBasicRemote().sendObject(msg);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        //群发消息
//        for (JsrChatWebSocketEndpoint item : webSocketSet) {
//            try {
//                item.sendMessage(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//                continue;
//            }
//        }
//    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误,sessionId:{}, errorMessage:{}", session.getId(), error.getMessage());
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

}
