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
import io.piper.server.spring.dto.ImUserDTO;
import io.piper.server.spring.pojo.entity.ImUser;
import io.piper.server.spring.pojo.mapper.ImUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Resource
    private ImUserMapper imUserMapper;

    public List<ImUserDTO> friends() {
        List<ImUserDTO> result = new ArrayList<>();
        List<ImUser> imUsers = imUserMapper.selectByExample(null);
        for (ImUser imUser : imUsers) {
            ImUserDTO dto = new ImUserDTO();
            BeanUtil.copyProperties(imUser, dto);
            result.add(dto);
        }
        return result;
    }
}
