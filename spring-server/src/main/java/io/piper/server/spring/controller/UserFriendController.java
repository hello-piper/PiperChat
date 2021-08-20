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
package io.piper.server.spring.controller;

import io.piper.common.exception.IMResult;
import io.piper.server.spring.dto.AddFriendDTO;
import io.piper.server.spring.service.ImUserFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "好友接口")
@RestController
@RequestMapping("/user-friend")
public class UserFriendController {

    @Resource
    private ImUserFriendService userFriendService;

    @PostMapping("add-friend")
    @ApiOperation("添加好友")
    public IMResult<Boolean> addFriend(@RequestBody AddFriendDTO dto) {
        return IMResult.ok(userFriendService.addFriend(dto));
    }
}
