package piper.im.web_server;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import piper.im.common.constant.Constants;
import piper.im.common.dao.MessageDAO;
import piper.im.common.enums.ChatTypeEnum;
import piper.im.common.enums.MessageTypeEnum;
import piper.im.common.exception.IMErrorEnum;
import piper.im.common.exception.IMException;
import piper.im.common.load_banlance.AddressLoadBalanceHandler;
import piper.im.common.load_banlance.IAddressLoadBalance;
import piper.im.common.pojo.message.Message;
import piper.im.common.util.RedisDS;
import piper.im.repository.impl.MessageDAOImpl;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChatServlet extends HttpServlet {
    private final IAddressLoadBalance addressHandler = new AddressLoadBalanceHandler();
    MessageDAO messageDAO = new MessageDAOImpl();

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
        Message message = JSONObject.parseObject(chatMsg, Message.class);
        Byte messageType = message.getMessageType();
        Byte chatType = message.getChatType();
        String from = message.getFrom();
        String to = message.getTo();
        if (ObjectUtil.hasNull(messageType, chatType, from, to)) {
            throw IMException.build(IMErrorEnum.PARAM_ERROR);
        }
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.valueOf(messageType);
        ChatTypeEnum chatTypeEnum = ChatTypeEnum.valueOf(chatType);
        if (ObjectUtil.hasNull(messageTypeEnum, chatTypeEnum)) {
            throw IMException.build(IMErrorEnum.PARAM_ERROR);
        }
        long msgId = IdUtil.getSnowflake(0, 0).nextId();
        message.setId(msgId);

        piper.im.common.pojo.entity.Message msg = new piper.im.common.pojo.entity.Message();
        msg.setId(msgId);
        msg.setMessageType(messageType);
        msg.setChatType(chatType);
        msg.setFrom(from);
        msg.setTo(to);
        msg.setBody(JSONObject.toJSONString(message.getBody()));
        this.messageDAO.insert(msg);

        Jedis jedis = RedisDS.getJedis();
        jedis.publish(Constants.CHANNEL_IM_MESSAGE, chatMsg);
        jedis.close();
    }
}
