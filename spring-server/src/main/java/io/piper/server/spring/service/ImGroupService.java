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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.util.LoginUserHolder;
import io.piper.common.util.Snowflake;
import io.piper.common.util.StringUtil;
import io.piper.server.spring.dto.ImGroupDTO;
import io.piper.server.spring.dto.PageVO;
import io.piper.server.spring.dto.page_dto.GroupSearchDTO;
import io.piper.server.spring.dto.page_dto.ImGroupPageDTO;
import io.piper.server.spring.dto.param.CreateGroupParam;
import io.piper.server.spring.pojo.entity.ImGroup;
import io.piper.server.spring.pojo.entity.ImGroupExample;
import io.piper.server.spring.pojo.entity.ImGroupUser;
import io.piper.server.spring.pojo.mapper.ImGroupMapper;
import io.piper.server.spring.pojo.mapper.ImGroupUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImGroupService {

    @Resource
    private Snowflake snowflake;

    @Resource
    private ImGroupMapper imGroupMapper;

    @Resource
    private ImGroupUserMapper imGroupUserMapper;

    public PageVO<ImGroupDTO> page(ImGroupPageDTO pageDTO) {
        Page<Object> page = PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        ImGroupExample example = new ImGroupExample();
        List<ImGroup> imGroups = imGroupMapper.selectByExample(example);
        List<ImGroupDTO> list = new ArrayList<>();
        for (ImGroup group : imGroups) {
            ImGroupDTO dto = new ImGroupDTO();
            BeanUtils.copyProperties(group, dto);
            list.add(dto);
        }
        return PageVO.build(list, page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal());
    }

    public boolean add(ImGroupDTO dto, UserTokenDTO userTokenDTO) {
        if (StringUtil.isEmpty(dto.getName())) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImGroup group = new ImGroup();
        BeanUtils.copyProperties(dto, group);
        group.setId(snowflake.nextId());
        group.setCreateUid(userTokenDTO.getId());
        group.setCreateTime(System.currentTimeMillis());
        imGroupMapper.insertSelective(group);
        return true;
    }

    public boolean update(ImGroupDTO dto, UserTokenDTO userTokenDTO) {
        if (StringUtil.isAnyEmpty(dto.getId(), dto.getName())) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImGroup group = imGroupMapper.selectByPrimaryKey(dto.getId());
        BeanUtils.copyProperties(dto, group);
        imGroupMapper.updateByPrimaryKeySelective(group);
        return true;
    }

    public boolean delete(Long id, UserTokenDTO userTokenDTO) {
        if (StringUtil.isEmpty(id)) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        imGroupMapper.deleteByPrimaryKey(id);
        return true;
    }

    public ImGroupDTO detail(Long id) {
        if (StringUtil.isEmpty(id)) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImGroup group = imGroupMapper.selectByPrimaryKey(id);
        ImGroupDTO dto = new ImGroupDTO();
        BeanUtils.copyProperties(group, dto);
        return dto;
    }

    public Boolean createGroup(CreateGroupParam param) {
        if (StringUtil.isAnyEmpty(param, param.getName(), param.getMembers())) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        Long curUid = LoginUserHolder.get().getId();
        long groupId = snowflake.nextId();
        long now = System.currentTimeMillis();
        ImGroup group = new ImGroup();
        group.setId(groupId);
        group.setName(param.getName());
        group.setCreateUid(curUid);
        group.setCreateTime(now);
        imGroupMapper.insertSelective(group);
        param.getMembers().add(curUid);
        for (Long uid : param.getMembers()) {
            ImGroupUser groupUser = new ImGroupUser();
            groupUser.setId(snowflake.nextId());
            groupUser.setGroupId(groupId);
            groupUser.setUid(uid);
            groupUser.setCreateUid(curUid);
            groupUser.setCreateTime(now);
            imGroupUserMapper.insertSelective(groupUser);
        }
        return true;
    }

    public PageVO<ImGroupDTO> myGroups(GroupSearchDTO searchDTO) {
        Page<Object> page = PageHelper.startPage(searchDTO.getPageNum(), searchDTO.getPageSize());
        ImGroupExample example = new ImGroupExample();
        List<ImGroup> imGroups = imGroupMapper.selectByExample(example);
        List<ImGroupDTO> list = new ArrayList<>();
        for (ImGroup group : imGroups) {
            ImGroupDTO dto = new ImGroupDTO();
            BeanUtils.copyProperties(group, dto);
            list.add(dto);
        }
        return PageVO.build(list, page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal());
    }
}