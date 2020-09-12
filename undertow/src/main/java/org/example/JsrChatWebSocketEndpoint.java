package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.coder.JsonDecode;
import org.example.coder.JsonEncode;
import org.example.coder.MsgpackDecode;
import org.example.coder.MsgpackEncode;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CopyOnWriteArraySet;


@ServerEndpoint(value = "/web-socket", encoders = {JsonEncode.class, MsgpackEncode.class}, decoders = {JsonDecode.class, MsgpackDecode.class})
public class JsrChatWebSocketEndpoint {

    private final Logger logger = LogManager.getLogger(JsrChatWebSocketEndpoint.class);

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的JsrChatWebSocketEndpoint对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static final CopyOnWriteArraySet<JsrChatWebSocketEndpoint> webSocketSet = new CopyOnWriteArraySet<JsrChatWebSocketEndpoint>();

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
        webSocketSet.add(this);    //加入set中
        addOnlineCount();          //在线数加1
        logger.debug("有新连接加入！当前在线人数为 {}", getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); //从set中删除
        subOnlineCount();          //在线数减1
        logger.debug("有一连接关闭！当前在线人数为 {}", getOnlineCount());
    }

    @OnMessage
    public void message(PongMessage message, Session session) throws IOException {
        byte[] chars = "pong".getBytes(StandardCharsets.UTF_8);
        ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate = allocate.put(chars);
        session.getAsyncRemote().sendPong(allocate);
    }

    @OnMessage
    public void message(Message message, Session session) throws IOException, EncodeException {
        logger.debug("来自客户端的消息 {}", message);
        this.session.getBasicRemote().sendObject(message);
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
        logger.error("发生错误,sessionId:{}, errorMessage:{}", session.getId(), error.getMessage());
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

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        JsrChatWebSocketEndpoint.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        JsrChatWebSocketEndpoint.onlineCount--;
    }

}
