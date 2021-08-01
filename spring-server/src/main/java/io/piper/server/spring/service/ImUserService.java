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
import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.util.StringUtil;
import io.piper.server.spring.dto.ImUserDTO;
import io.piper.server.spring.dto.PageVO;
import io.piper.server.spring.dto.page_dto.ImUserPageDTO;
import io.piper.server.spring.pojo.entity.ImUser;
import io.piper.server.spring.pojo.entity.ImUserExample;
import io.piper.server.spring.pojo.mapper.ImUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImUserService {

    @Resource
    private ImUserMapper imUserMapper;

    public PageVO<ImUserDTO> page(ImUserPageDTO pageDTO) {
        Page<Object> page = PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        ImUserExample example = new ImUserExample();
        List<ImUser> imGroups = imUserMapper.selectByExample(example);
        List<ImUserDTO> list = new ArrayList<>();
        for (ImUser user : imGroups) {
            ImUserDTO dto = new ImUserDTO();
            BeanUtil.copyProperties(user, dto);
            list.add(dto);
        }
        return PageVO.build(list, page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal());
    }

    public boolean add(ImUserDTO dto, UserTokenDTO userTokenDTO) {
        if (StringUtil.isAnyEmpty(dto.getEmail(), dto.getNickname(), dto.getPhone())) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImUser user = new ImUser();
        BeanUtil.copyProperties(dto, user);
        user.setId(IdUtil.getSnowflake(0, 0).nextId());
        user.setCreateUid(userTokenDTO.getId());
        user.setCreateTime(System.currentTimeMillis());
        imUserMapper.insertSelective(user);
        return true;
    }

    public boolean update(ImUserDTO dto, UserTokenDTO userTokenDTO) {
        if (StringUtil.isAnyEmpty(dto.getEmail(), dto.getNickname(), dto.getPhone())) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImUser user = imUserMapper.selectByPrimaryKey(dto.getId());
        BeanUtil.copyProperties(dto, user);
        imUserMapper.updateByPrimaryKeySelective(user);
        return true;
    }

    public boolean delete(Long id, UserTokenDTO userTokenDTO) {
        if (StringUtil.isEmpty(id)) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        imUserMapper.deleteByPrimaryKey(id);
        return true;
    }

    public ImUserDTO detail(Long id) {
        if (StringUtil.isEmpty(id)) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImUser user = imUserMapper.selectByPrimaryKey(id);
        ImUserDTO dto = new ImUserDTO();
        BeanUtil.copyProperties(user, dto);
        return dto;
    }
}