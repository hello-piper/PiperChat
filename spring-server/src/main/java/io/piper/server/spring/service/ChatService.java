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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

import io.piper.common.constant.Constants;
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.enums.MsgTypeEnum;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.load_banlance.AddressLoadBalance;
import io.piper.common.pojo.config.AddressInfo;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.pojo.message.Msg;
import io.piper.common.util.LoginUserHolder;
import io.piper.common.util.Snowflake;
import io.piper.common.util.StringUtil;
import io.piper.server.spring.dto.ActiveContactVO;
import io.piper.server.spring.dto.ImMessageDTO;
import io.piper.server.spring.dto.page_dto.MsgSearchDTO;
import io.piper.server.spring.pojo.entity.ImGroup;
import io.piper.server.spring.pojo.entity.ImMessage;
import io.piper.server.spring.pojo.entity.ImUser;
import io.piper.server.spring.pojo.mapper.ImGroupMapper;
import io.piper.server.spring.pojo.mapper.ImMessageMapperExt;
import io.piper.server.spring.pojo.mapper.ImUserMapper;

@Service
public class ChatService {
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    @Resource
    private Snowflake snowflake;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ImMessageMapperExt imMessageMapperExt;

    @Resource
    private ImUserMapper imUserMapper;

    @Resource
    private ImGroupMapper imGroupMapper;

    public AddressInfo getAddress() {
        return AddressLoadBalance.getAddressByWeight();
    }

    public Msg chat(Msg msg) {
        log.debug("chat msg:{}", msg);

        ChatTypeEnum chatTypeEnum = msg.getChatTypeEnum();
        MsgTypeEnum msgTypeEnum = msg.getTypeEnum();
        if (StringUtil.isAnyEmpty(msgTypeEnum, chatTypeEnum, msg.getFrom(), msg.getTo())) {
            throw IMException.build(IMErrorEnum.PARAM_ERROR);
        }
        UserTokenDTO loginUser = LoginUserHolder.get();
        long msgId = snowflake.nextId();
        long now = System.currentTimeMillis();
        msg.setFrom(loginUser.getId());
        msg.setId(msgId);

        ImMessage message = new ImMessage();
        message.setId(msgId);
        message.setMsgType(msg.getType());
        message.setChatType(msg.getChatType());
        message.setChatId(msg.getChatId());
        message.setFrom(msg.getFrom());
        message.setTo(JSON.toJSONString(msg.getTo()));
        message.setTime(now / 1000);
        message.setText(msg.getText());
        message.setBody(msg.getBodyStr());
        imMessageMapperExt.insert(message);

        redisTemplate.convertAndSend(Constants.CHANNEL_IM_MESSAGE, msg);
        return msg;
    }

    public void subRoom(Msg msg) {
        log.debug("subRoom msg:{}", msg);
        if (StringUtil.isAnyEmpty(msg.getTypeEnum(),msg.getChatTypeEnum(), msg.getBody())) {
            throw IMException.build(IMErrorEnum.PARAM_ERROR);
        }
        msg.setFrom(LoginUserHolder.get().getId());
        redisTemplate.convertAndSend(Constants.CHANNEL_IM_MESSAGE, msg);
    }

    public List<ImMessageDTO> chatRecord(MsgSearchDTO dto) {
        return imMessageMapperExt.selectMessage(dto.getLastMsgId(), dto.getConversationId(), dto.getTotal());
    }

    public Collection<ActiveContactVO> activeContacts() {
        List<ImMessageDTO> imMessages = imMessageMapperExt.activeContacts();
        if (CollectionUtils.isEmpty(imMessages)) {
            return Collections.emptyList();
        }
        Collections.reverse(imMessages);
        Map<String, ActiveContactVO> map = new LinkedHashMap<>();
        for (ImMessageDTO dto : imMessages) {
            String conversationId = dto.getConversationId();
            ActiveContactVO vo = map.get(conversationId);
            if (vo == null) {
                vo = new ActiveContactVO();
                vo.setChatType(dto.getChatType());
                vo.setConversationId(conversationId);
                map.put(conversationId, vo);
                if (ChatTypeEnum.SINGLE.type.equals(dto.getChatType())) {
                    Long id = dto.getFrom();
                    if (Objects.equals(LoginUserHolder.getUid(), dto.getFrom())) {
                        id = dto.getTo();
                    }
                    vo.setId(id);
                    ImUser imUser = imUserMapper.selectByPrimaryKey(id);
                    if (!Objects.isNull(imUser)) {
                        vo.setName(imUser.getNickname());
                        vo.setAvatar(imUser.getAvatar());
                    }
                } else if (ChatTypeEnum.GROUP.type.equals(dto.getChatType())) {
                    vo.setId(dto.getTo());
                    ImGroup imGroup = imGroupMapper.selectByPrimaryKey(vo.getId());
                    if (!Objects.isNull(imGroup)) {
                        vo.setName(imGroup.getName());
                        vo.setAvatar(imGroup.getAvatar());
                    }
                }
            }
            vo.addMessageDTO(dto);
        }
        return map.values();
    }
}
