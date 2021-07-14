package piper.im.web_server;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import piper.im.common.constant.Constants;
import piper.im.common.enums.ChatTypeEnum;
import piper.im.common.enums.MsgTypeEnum;
import piper.im.common.exception.IMErrorEnum;
import piper.im.common.exception.IMException;
import piper.im.common.load_banlance.AddressLoadBalanceHandler;
import piper.im.common.load_banlance.IAddressLoadBalance;
import piper.im.common.pojo.entity.Message;
import piper.im.common.pojo.message.Msg;
import piper.im.common.util.RedisDS;
import piper.im.web_server.repository.dao.MessageDAO;
import piper.im.web_server.repository.impl.MessageDAOImpl;
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

        Message message = new Message();
        message.setId(msgId);
        message.setMsgType(msg.getMsgType());
        message.setChatType(msg.getChatType());
        message.setFrom(from);
        message.setTo(to);
        message.setBody(msg.getBodyStr());
        messageDAO.insert(message);

        Jedis jedis = RedisDS.getJedis();
        jedis.publish(Constants.CHANNEL_IM_MESSAGE, chatMsg);
        jedis.close();
    }
}
