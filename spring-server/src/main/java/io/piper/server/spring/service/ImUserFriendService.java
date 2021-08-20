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
import io.piper.common.enums.ChatTypeEnum;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.pojo.message.Msg;
import io.piper.common.util.LoginUserHolder;
import io.piper.common.util.StringUtil;
import io.piper.server.spring.dto.AddFriendDTO;
import io.piper.server.spring.dto.ImUserFriendDTO;
import io.piper.server.spring.dto.PageVO;
import io.piper.server.spring.dto.page_dto.ImUserFriendPageDTO;
import io.piper.server.spring.pojo.entity.ImUserFriend;
import io.piper.server.spring.pojo.entity.ImUserFriendExample;
import io.piper.server.spring.pojo.mapper.ImUserFriendMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImUserFriendService {

    @Resource
    private ImUserFriendMapper imUserFriendMapper;

    public PageVO<ImUserFriendDTO> page(ImUserFriendPageDTO pageDTO) {
        Page<Object> page = PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        ImUserFriendExample example = new ImUserFriendExample();
        List<ImUserFriend> imGroups = imUserFriendMapper.selectByExample(example);
        List<ImUserFriendDTO> list = new ArrayList<>();
        for (ImUserFriend userFriend : imGroups) {
            ImUserFriendDTO dto = new ImUserFriendDTO();
            BeanUtil.copyProperties(userFriend, dto);
            list.add(dto);
        }
        return PageVO.build(list, page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal());
    }

    public boolean add(ImUserFriendDTO dto, UserTokenDTO userTokenDTO) {
        if (StringUtil.isAnyEmpty(dto.getUid(), dto.getFriendId())) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImUserFriend userFriend = new ImUserFriend();
        BeanUtil.copyProperties(dto, userFriend);
        userFriend.setId(Msg.getConversation(ChatTypeEnum.SINGLE.type, dto.getUid(), dto.getFriendId()));
        userFriend.setCreateTime(System.currentTimeMillis());
        imUserFriendMapper.insertSelective(userFriend);
        return true;
    }

    public boolean update(ImUserFriendDTO dto, UserTokenDTO userTokenDTO) {
        if (StringUtil.isAnyEmpty(dto.getUid(), dto.getFriendId())) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImUserFriend userFriend = imUserFriendMapper.selectByPrimaryKey(dto.getId());
        BeanUtil.copyProperties(dto, userFriend);
        imUserFriendMapper.updateByPrimaryKeySelective(userFriend);
        return true;
    }

    public boolean delete(String id, UserTokenDTO userTokenDTO) {
        if (StringUtil.isEmpty(id)) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        imUserFriendMapper.deleteByPrimaryKey(id);
        return true;
    }

    public ImUserFriendDTO detail(String id) {
        if (StringUtil.isEmpty(id)) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImUserFriend userFriend = imUserFriendMapper.selectByPrimaryKey(id);
        ImUserFriendDTO dto = new ImUserFriendDTO();
        BeanUtil.copyProperties(userFriend, dto);
        return dto;
    }

    public Boolean addFriend(AddFriendDTO dto) {
        UserTokenDTO loginUser = LoginUserHolder.get();
        if (StringUtil.isAnyEmpty(dto, dto.getFriendId(), loginUser)) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImUserFriend friend = new ImUserFriend();
        friend.setId(Msg.getConversation(ChatTypeEnum.SINGLE.type, loginUser.getId(), dto.getFriendId()));
        friend.setUid(loginUser.getId());
        friend.setFriendId(dto.getFriendId());
        friend.setReqMsg(dto.getReqMsg());
        friend.setCreateTime(System.currentTimeMillis());
        friend.setStatus(0);
        imUserFriendMapper.insertSelective(friend);
        return true;
    }
}