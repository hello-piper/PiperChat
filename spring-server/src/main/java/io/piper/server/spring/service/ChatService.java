package io.piper.server.spring.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import io.piper.server.spring.pojo.entity.ImMessage;
import io.piper.server.spring.pojo.mapper.ImMessageMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import io.piper.common.constant.Constants;
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.enums.MsgTypeEnum;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.load_banlance.AddressLoadBalanceHandler;
import io.piper.common.load_banlance.IAddressLoadBalance;
import io.piper.common.pojo.config.AddressInfo;
import io.piper.common.pojo.message.Msg;

import javax.annotation.Resource;

@Service
public class ChatService {

    @Resource
    private ImMessageMapper imMessageMapper;

    @Resource
    private RedisTemplate redisTemplate;

    private static final IAddressLoadBalance addressHandler = new AddressLoadBalanceHandler();

    public AddressInfo getAddress() {
        return addressHandler.getAddressByWeight();
    }

    public void chat(Msg msg) {
        ChatTypeEnum chatTypeEnum = msg.getChatTypeEnum();
        MsgTypeEnum msgTypeEnum = msg.getMsgTypeEnum();
        String from = msg.getFrom();
        String to = msg.getTo();
        if (ObjectUtil.hasNull(msgTypeEnum, chatTypeEnum, from, to)) {
            throw IMException.build(IMErrorEnum.PARAM_ERROR);
        }
        long now = System.currentTimeMillis();
        long msgId = IdUtil.getSnowflake(0, 0).nextId();
        msg.setTimestamp(now);
        msg.setId(msgId);

        ImMessage message = new ImMessage();
        message.setId(msgId);
        message.setMsgType(msg.getMsgType());
        message.setChatType(msg.getChatType());
        message.setFrom(from);
        message.setTo(to);
        message.setBody(msg.getBodyStr());
        message.setCreateTime(now);
        imMessageMapper.insert(message);

        redisTemplate.convertAndSend(Constants.CHANNEL_IM_MESSAGE, msg.toString());
    }
}
