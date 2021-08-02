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

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.piper.common.constant.Constants;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.util.Snowflake;
import io.piper.common.util.StringUtil;
import io.piper.server.spring.dto.ImMessageDTO;
import io.piper.server.spring.dto.PageVO;
import io.piper.server.spring.dto.page_dto.ImMessagePageDTO;
import io.piper.server.spring.pojo.entity.ImMessage;
import io.piper.server.spring.pojo.entity.ImMessageExample;
import io.piper.server.spring.pojo.mapper.ImMessageMapper;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImMessageService {

    @Resource
    private JedisPool jedisPool;

    @Resource
    private ImMessageMapper imMessageMapper;

    public PageVO<ImMessageDTO> page(ImMessagePageDTO pageDTO) {
        Page<Object> page = PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        ImMessageExample example = new ImMessageExample();
        List<ImMessage> imGroups = imMessageMapper.selectByExample(example);
        List<ImMessageDTO> list = new ArrayList<>();
        for (ImMessage message : imGroups) {
            ImMessageDTO dto = new ImMessageDTO();
            BeanUtil.copyProperties(message, dto);
            list.add(dto);
        }
        return PageVO.build(list, page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal());
    }

    public boolean add(ImMessageDTO dto, UserTokenDTO userTokenDTO) {
        if (StringUtil.isAnyEmpty(dto.getChatType(), dto.getMsgType(), dto.getTo())) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        Snowflake snowflake = Snowflake.getSnowflake(jedisPool.getResource(), Constants.IM_WORK_ID);
        ImMessage message = new ImMessage();
        BeanUtil.copyProperties(dto, message);
        message.setId(snowflake.nextId());
        message.setCreateTime(System.currentTimeMillis());
        imMessageMapper.insertSelective(message);
        return true;
    }

    public boolean update(ImMessageDTO dto, UserTokenDTO userTokenDTO) {
        if (StringUtil.isAnyEmpty(dto.getChatType(), dto.getMsgType(), dto.getTo())) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImMessage message = imMessageMapper.selectByPrimaryKey(dto.getId());
        BeanUtil.copyProperties(dto, message);
        imMessageMapper.updateByPrimaryKeySelective(message);
        return true;
    }

    public boolean delete(Long id, UserTokenDTO userTokenDTO) {
        if (StringUtil.isEmpty(id)) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        imMessageMapper.deleteByPrimaryKey(id);
        return true;
    }

    public ImMessageDTO detail(Long id) {
        if (StringUtil.isEmpty(id)) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImMessage message = imMessageMapper.selectByPrimaryKey(id);
        ImMessageDTO dto = new ImMessageDTO();
        BeanUtil.copyProperties(message, dto);
        return dto;
    }
}