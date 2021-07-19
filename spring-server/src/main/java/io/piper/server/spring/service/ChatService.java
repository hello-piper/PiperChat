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
package io.piper.server.spring.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import io.jsonwebtoken.lang.Collections;
import io.piper.common.constant.Constants;
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.enums.MsgTypeEnum;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.load_banlance.AddressLoadBalanceHandler;
import io.piper.common.load_banlance.IAddressLoadBalance;
import io.piper.common.pojo.config.AddressInfo;
import io.piper.common.pojo.message.Msg;
import io.piper.server.spring.pojo.entity.ImMessage;
import io.piper.server.spring.pojo.mapper.ImMessageMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@Log4j2
@Service
public class ChatService {

    @Resource
    private ImMessageMapper imMessageMapper;

    @Autowired
    private JedisPool jedisPool;

    @Resource
    private RedisTemplate redisTemplate;

    private static final IAddressLoadBalance ADDRESS_HANDLER = new AddressLoadBalanceHandler();

    @PostConstruct
    public void init() {
        new Thread(() -> {
            Jedis jedis = jedisPool.getResource();
            Map<String, String> imServerMap = jedis.hgetAll(Constants.IM_SERVER_HASH);
            if (!Collections.isEmpty(imServerMap)) {
                for (String info : imServerMap.values()) {
                    ADDRESS_HANDLER.flushAddress(JSONUtil.toBean(info, AddressInfo.class));
                }
            }
            jedis.subscribe(new JedisPubSub() {
                @Override
                public void onMessage(String channel, String message) {
                    log.info("receiveMessage >>> channel:{} message:{}", channel, message);
                    if (channel.equals(Constants.CHANNEL_IM_RENEW)) {
                        ADDRESS_HANDLER.flushAddress(JSONUtil.toBean(message, AddressInfo.class));
                    } else if (channel.equals(Constants.CHANNEL_IM_SHUTDOWN)) {
                        ADDRESS_HANDLER.removeAddress(JSONUtil.toBean(message, AddressInfo.class));
                    }
                }
            }, Constants.CHANNEL_IM_RENEW, Constants.CHANNEL_IM_SHUTDOWN);
        }, "web-server-task-thread").start();
    }

    public AddressInfo getAddress() {
        return ADDRESS_HANDLER.getAddressByWeight();
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
