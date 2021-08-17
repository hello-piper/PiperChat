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

import cn.hutool.json.JSONUtil;
import io.piper.common.constant.Constants;
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.enums.MsgTypeEnum;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.load_banlance.AddressLoadBalanceHandler;
import io.piper.common.load_banlance.IAddressLoadBalance;
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
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Log4j2
@Service
public class ChatService {

    @Resource
    private Snowflake snowflake;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private ImMessageMapperExt imMessageMapperExt;

    @Resource
    private ImUserMapper imUserMapper;

    @Resource
    private ImGroupMapper imGroupMapper;

    public static final IAddressLoadBalance ADDRESS_HANDLER = new AddressLoadBalanceHandler();

    public AddressInfo getAddress() {
        return ADDRESS_HANDLER.getAddressByWeight();
    }

    public Msg chat(Msg msg) {
        log.debug("chat msg:{}", msg);

        ChatTypeEnum chatTypeEnum = msg.getChatTypeEnum();
        MsgTypeEnum msgTypeEnum = msg.getMsgTypeEnum();
        Long to = msg.getTo();
        if (StringUtil.isAnyEmpty(msgTypeEnum, chatTypeEnum, to)) {
            throw IMException.build(IMErrorEnum.PARAM_ERROR);
        }
        UserTokenDTO loginUser = LoginUserHolder.get();
        long msgId = snowflake.nextId();
        long now = System.currentTimeMillis();
        msg.setFrom(loginUser.getId());
        msg.setTimestamp(now);
        msg.setId(msgId);

        ImMessage message = new ImMessage();
        message.setId(msgId);
        message.setMsgType(msg.getMsgType());
        message.setChatType(msg.getChatType());
        message.setFrom(loginUser.getId());
        message.setTo(to);
        message.setBody(msg.getBodyStr());
        message.setCreateTime(now);
        message.setExtra(Objects.isNull(msg.getExtra()) ? "" : JSONUtil.toJsonStr(msg.getExtra()));
        imMessageMapperExt.insert(message);

        redisTemplate.convertAndSend(Constants.CHANNEL_IM_MESSAGE, msg);
        return msg;
    }

    public void subRoom(Msg msg) {
        log.debug("subRoom msg:{}", msg);
        if (StringUtil.isAnyEmpty(msg.getMsgTypeEnum(), msg.getCmdMsgBody())) {
            throw IMException.build(IMErrorEnum.PARAM_ERROR);
        }
        msg.setFrom(LoginUserHolder.get().getId());
        redisTemplate.convertAndSend(Constants.CHANNEL_IM_MESSAGE, msg);
    }

    public List<ImMessageDTO> chatRecord(MsgSearchDTO dto) {
        return imMessageMapperExt.selectMessage(dto.getLastMsgId(), LoginUserHolder.get().getId(), dto.getTo(), dto.getTotal());
    }

    public Collection<ActiveContactVO> activeContacts() {
        List<ImMessageDTO> imMessages = imMessageMapperExt.activeContacts();
        if (CollectionUtils.isEmpty(imMessages)) {
            return Collections.emptyList();
        }
        Map<String, ActiveContactVO> map = new LinkedHashMap<>();
        for (ImMessageDTO dto : imMessages) {
            Long to = dto.getTo();
            Byte chatType = dto.getChatType();
            String key = chatType + "-" + to;
            ActiveContactVO vo = map.get(key);
            if (vo == null) {
                vo = new ActiveContactVO();
                vo.setTo(to);
                vo.setChatType(chatType);
                fillToInfo(vo);
                map.put(key, vo);
            }
            vo.addMessageDTO(dto);
        }
        return map.values();
    }

    private void fillToInfo(ActiveContactVO vo) {
        Long to = vo.getTo();
        Byte chatType = vo.getChatType();
        if (ChatTypeEnum.SINGLE.type.equals(chatType)) {
            ImUser imUser = imUserMapper.selectByPrimaryKey(to);
            if (!Objects.isNull(imUser)) {
                vo.setName(imUser.getNickname());
                vo.setAvatar(imUser.getAvatar());
            }
        } else if (ChatTypeEnum.GROUP.type.equals(chatType)) {
            ImGroup imGroup = imGroupMapper.selectByPrimaryKey(to);
            if (!Objects.isNull(imGroup)) {
                vo.setName(imGroup.getName());
                vo.setAvatar(imGroup.getAvatar());
            }
        }
    }
}
