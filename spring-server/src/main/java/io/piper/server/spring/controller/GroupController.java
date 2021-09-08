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
import io.piper.server.spring.dto.ImGroupDTO;
import io.piper.server.spring.dto.PageVO;
import io.piper.server.spring.dto.page_dto.GroupSearchDTO;
import io.piper.server.spring.dto.param.CreateGroupParam;
import io.piper.server.spring.service.ImGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "群组接口")
@RestController
@RequestMapping("/group")
public class GroupController {

    @Resource
    private ImGroupService groupService;

    @PostMapping("create")
    @ApiOperation("创建群组")
    public IMResult<Boolean> createGroup(@RequestBody CreateGroupParam param) {
        return IMResult.ok(groupService.createGroup(param));
    }

    @PostMapping("my-groups")
    @ApiOperation("查询我的群组")
    public IMResult<PageVO<ImGroupDTO>> myGroups(@RequestBody GroupSearchDTO searchDTO) {
        return IMResult.ok(groupService.myGroups(searchDTO));
    }
}
