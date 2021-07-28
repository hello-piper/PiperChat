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
        return false;
    }

    public boolean update(ImUserFriendDTO dto, UserTokenDTO userTokenDTO) {
        return false;
    }

    public boolean delete(Long id, UserTokenDTO userTokenDTO) {
        return false;
    }

    public ImUserFriendDTO detail(Long id) {
        return null;
    }
}