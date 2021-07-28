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
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.server.spring.dto.ImGroupUserDTO;
import io.piper.server.spring.dto.PageVO;
import io.piper.server.spring.dto.page_dto.ImGroupUserPageDTO;
import io.piper.server.spring.pojo.entity.ImGroupUser;
import io.piper.server.spring.pojo.entity.ImGroupUserExample;
import io.piper.server.spring.pojo.mapper.ImGroupUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImGroupUserService {

    @Resource
    private ImGroupUserMapper imGroupUserMapper;

    public PageVO<ImGroupUserDTO> page(ImGroupUserPageDTO pageDTO) {
        Page<Object> page = PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getPageSize());
        ImGroupUserExample example = new ImGroupUserExample();
        List<ImGroupUser> imGroups = imGroupUserMapper.selectByExample(example);
        List<ImGroupUserDTO> list = new ArrayList<>();
        for (ImGroupUser groupUser : imGroups) {
            ImGroupUserDTO dto = new ImGroupUserDTO();
            BeanUtil.copyProperties(groupUser, dto);
            list.add(dto);
        }
        return PageVO.build(list, page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal());
    }

    public boolean add(ImGroupUserDTO dto, UserTokenDTO userTokenDTO) {
        return false;
    }

    public boolean update(ImGroupUserDTO dto, UserTokenDTO userTokenDTO) {
        return false;
    }

    public boolean delete(Long id, UserTokenDTO userTokenDTO) {
        return false;
    }

    public ImGroupUserDTO detail(Long id) {
        return null;
    }
}