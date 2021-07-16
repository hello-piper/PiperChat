/*
 * Copyright 2020 The PiperChat
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

import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.server.spring.dto.ImUserFriendDTO;
import io.piper.server.spring.pojo.mapper.ImUserFriendMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ImUserFriendService {

    @Resource
    private ImUserFriendMapper imUserFriendMapper;

    public PageImpl<ImUserFriendDTO> page(Integer pageNum, Integer pageSize) {
        return null;
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