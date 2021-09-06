/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.server.web;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.json.JSONUtil;
import io.piper.common.constant.Constants;
import io.piper.common.db.RedisDS;
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.enums.MsgTypeEnum;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.load_banlance.AddressLoadBalanceHandler;
import io.piper.common.load_banlance.IAddressLoadBalance;
import io.piper.common.pojo.entity.ImMessage;
import io.piper.common.pojo.message.Msg;
import io.piper.common.util.IdUtil;
import io.piper.common.util.Snowflake;
import io.piper.common.util.StringUtil;
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
    private static final Snowflake snowflake = IdUtil.getSnowflake(Constants.IM_WORK_ID);

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
        if (StringUtil.isAnyEmpty(msgTypeEnum, chatTypeEnum, msg.getFrom(), msg.getTo())) {
            throw IMException.build(IMErrorEnum.PARAM_ERROR);
        }
        long msgId = snowflake.nextId();
        long now = System.currentTimeMillis();
        msg.setServerTime(now);
        msg.setId(msgId);

        ImMessage message = new ImMessage();
        message.setId(msgId);
        message.setMsgType(msg.getMsgType());
        message.setChatType(msg.getChatType());
        message.setConversationId(msg.getAndSetConversation());
        message.setFrom(msg.getFrom());
        message.setTo(msg.getTo());
        message.setSendTime(msg.getSendTime());
        message.setServerTime(now);
        message.setTitle(msg.getTitle());
        message.setImageMsgBody(msg.getImageMsgBody() == null ? null : msg.getImageMsgBody().toString());
        message.setVoiceMsgBody(msg.getVoiceMsgBody() == null ? null : msg.getVoiceMsgBody().toString());
        message.setVideoMsgBody(msg.getVideoMsgBody() == null ? null : msg.getVideoMsgBody().toString());
        message.setFileMsgBody(msg.getFileMsgBody() == null ? null : msg.getFileMsgBody().toString());
        message.setLocationMsgBody(msg.getLocationMsgBody() == null ? null : msg.getLocationMsgBody().toString());
        message.setCmdMsgBody(msg.getCmdMsgBody() == null ? null : msg.getCmdMsgBody().toString());
        message.setExtra(msg.getExtra() == null ? null : JSONUtil.toJsonStr(msg.getExtra()));
        messageDAO.insert(message);

        Jedis jedis = RedisDS.getJedis();
        jedis.publish(Constants.CHANNEL_IM_MESSAGE, chatMsg);
        jedis.close();

        IoUtil.writeUtf8(resp.getOutputStream(), true, msg);
    }
}
