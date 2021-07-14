package io.piper.server.web;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import io.piper.common.constant.Constants;
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.enums.MsgTypeEnum;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.load_banlance.AddressLoadBalanceHandler;
import io.piper.common.load_banlance.IAddressLoadBalance;
import io.piper.common.pojo.entity.ImMessage;
import io.piper.common.pojo.message.Msg;
import io.piper.common.util.RedisDS;
import io.piper.server.web.repository.dao.MessageDAO;
import io.piper.server.web.repository.impl.MessageDAOImpl;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ChatServlet
 *
 * @author piper
 */
public class ChatServlet extends HttpServlet {
    private static final IAddressLoadBalance addressHandler = new AddressLoadBalanceHandler();
    private static final MessageDAO messageDAO = Singleton.get(MessageDAOImpl.class);

    /**
     * 前端获取可用服务地址
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String data = JSONUtil.toJsonStr(addressHandler.getAddressByWeight());
        IoUtil.writeUtf8(resp.getOutputStream(), true, data);
    }

    /**
     * 接收用户发送消息
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String chatMsg = IoUtil.read(req.getReader());
        Msg msg = JSONUtil.toBean(chatMsg, Msg.class);
        ChatTypeEnum chatTypeEnum = msg.getChatTypeEnum();
        MsgTypeEnum msgTypeEnum = msg.getMsgTypeEnum();
        String from = msg.getFrom();
        String to = msg.getTo();
        if (ObjectUtil.hasNull(msgTypeEnum, chatTypeEnum, from, to)) {
            throw IMException.build(IMErrorEnum.PARAM_ERROR);
        }
        long msgId = IdUtil.getSnowflake(0, 0).nextId();
        msg.setId(msgId);

        ImMessage imMessage = new ImMessage();
        imMessage.setId(msgId);
        imMessage.setMsgType(msg.getMsgType());
        imMessage.setChatType(msg.getChatType());
        imMessage.setFrom(from);
        imMessage.setTo(to);
        imMessage.setBody(msg.getBodyStr());
        messageDAO.insert(imMessage);

        Jedis jedis = RedisDS.getJedis();
        jedis.publish(Constants.CHANNEL_IM_MESSAGE, chatMsg);
        jedis.close();
    }
}
