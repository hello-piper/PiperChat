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
import io.piper.common.util.StringUtil;
import io.piper.server.spring.dto.ImUserAdminDTO;
import io.piper.server.spring.dto.PageVO;
import io.piper.server.spring.dto.page_dto.ImUserAdminPageDTO;
import io.piper.server.spring.pojo.entity.ImUserAdmin;
import io.piper.server.spring.pojo.entity.ImUserAdminExample;
import io.piper.server.spring.pojo.mapper.ImUserAdminMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImUserAdminService {

    @Resource
    private ImUserAdminMapper imUserAdminMapper;

    public PageVO<ImUserAdminDTO> page(ImUserAdminPageDTO pageDTO) {
        Page<Object> page = PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        ImUserAdminExample example = new ImUserAdminExample();
        List<ImUserAdmin> imGroups = imUserAdminMapper.selectByExample(example);
        List<ImUserAdminDTO> list = new ArrayList<>();
        for (ImUserAdmin userAdmin : imGroups) {
            ImUserAdminDTO dto = new ImUserAdminDTO();
            BeanUtils.copyProperties(userAdmin, dto);
            list.add(dto);
        }
        return PageVO.build(list, page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal());
    }

    public boolean add(ImUserAdminDTO dto, UserTokenDTO userTokenDTO) {
        if (StringUtil.isEmpty(dto.getUid())) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImUserAdmin userAdmin = new ImUserAdmin();
        BeanUtils.copyProperties(dto, userAdmin);
        userAdmin.setCreateUid(userTokenDTO.getId());
        userAdmin.setCreateTime(System.currentTimeMillis());
        imUserAdminMapper.insertSelective(userAdmin);
        return true;
    }

    public boolean update(ImUserAdminDTO dto, UserTokenDTO userTokenDTO) {
        if (StringUtil.isEmpty(dto.getUid())) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImUserAdmin userAdmin = imUserAdminMapper.selectByPrimaryKey(dto.getUid());
        BeanUtils.copyProperties(dto, userAdmin);
        imUserAdminMapper.updateByPrimaryKeySelective(userAdmin);
        return true;
    }

    public boolean delete(Long uid, UserTokenDTO userTokenDTO) {
        if (StringUtil.isEmpty(uid)) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        imUserAdminMapper.deleteByPrimaryKey(uid);
        return true;
    }

    public ImUserAdminDTO detail(Long uid) {
        if (StringUtil.isEmpty(uid)) {
            throw new IMException(IMErrorEnum.PARAM_ERROR);
        }
        ImUserAdmin userAdmin = imUserAdminMapper.selectByPrimaryKey(uid);
        ImUserAdminDTO dto = new ImUserAdminDTO();
        BeanUtils.copyProperties(userAdmin, dto);
        return dto;
    }
}