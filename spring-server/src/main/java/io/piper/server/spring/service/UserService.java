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
import io.netty.util.internal.StringUtil;
import io.piper.common.util.LoginUserHolder;
import io.piper.server.spring.dto.ImUserDTO;
import io.piper.server.spring.dto.PageVO;
import io.piper.server.spring.dto.page_dto.FriendSearchDTO;
import io.piper.server.spring.pojo.entity.ImUser;
import io.piper.server.spring.pojo.entity.ImUserExample;
import io.piper.server.spring.pojo.mapper.ImUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService {

    @Resource
    private ImUserMapper imUserMapper;

    public ImUserDTO userInfo(Long uid) {
        if (Objects.isNull(uid)) {
            uid = LoginUserHolder.get().getId();
        }
        ImUser imUser = imUserMapper.selectByPrimaryKey(uid);
        if (Objects.isNull(imUser)) {
            return null;
        }
        ImUserDTO userDTO = new ImUserDTO();
        BeanUtil.copyProperties(imUser, userDTO);
        return userDTO;
    }

    public PageVO<ImUserDTO> friends(FriendSearchDTO searchDTO) {
        Page<Object> page = PageHelper.startPage(searchDTO.getPageNum(), searchDTO.getPageSize());
        ImUserExample example = new ImUserExample();
        if (!StringUtil.isNullOrEmpty(searchDTO.getNickname())) {
            example.createCriteria().andNicknameLike(searchDTO.getNickname());
        }
        List<ImUser> imGroups = imUserMapper.selectByExample(example);
        List<ImUserDTO> list = new ArrayList<>();
        for (ImUser user : imGroups) {
            ImUserDTO dto = new ImUserDTO();
            BeanUtil.copyProperties(user, dto);
            list.add(dto);
        }
        return PageVO.build(list, page.getPageNum(), page.getPageSize(), page.getPages(), page.getTotal());
    }
}
