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
package io.piper.server.spring.controller.admin;

import io.piper.common.exception.IMResult;
import io.piper.common.util.LoginUserHolder;
import io.piper.server.spring.dto.ImUserFriendDTO;
import io.piper.server.spring.dto.PageVO;
import io.piper.server.spring.dto.page_dto.ImUserFriendPageDTO;
import io.piper.server.spring.service.ImUserFriendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "后台 好友管理")
@RequestMapping("admin/im-user-friend")
public class AdminImUserFriendController {

    @Resource
    private ImUserFriendService imUserFriendService;

    @GetMapping("/page")
    @ApiOperation("好友管理 分页")
    public IMResult<PageVO<ImUserFriendDTO>> page(@RequestHeader("token") String token, @RequestBody ImUserFriendPageDTO pageDTO) {
        return IMResult.ok(imUserFriendService.page(pageDTO));
    }

    @PostMapping("/add")
    @ApiOperation("好友管理 添加")
    public IMResult<Boolean> add(@RequestHeader("token") String token, @RequestBody ImUserFriendDTO dto) {
        return IMResult.ok(imUserFriendService.add(dto, LoginUserHolder.get()));
    }

    @PutMapping("/update")
    @ApiOperation("好友管理 修改")
    public IMResult<Boolean> update(@RequestHeader("token") String token, @RequestBody ImUserFriendDTO dto) {
        return IMResult.ok(imUserFriendService.update(dto, LoginUserHolder.get()));
    }

    @DeleteMapping("/delete")
    @ApiOperation("好友管理 删除")
    public IMResult<Boolean> delete(@RequestHeader("token") String token, @RequestParam Long id) {
        return IMResult.ok(imUserFriendService.delete(id, LoginUserHolder.get()));
    }

    @GetMapping("/detail")
    @ApiOperation("好友管理 详情")
    public IMResult<ImUserFriendDTO> detail(@RequestHeader("token") String token, @RequestParam Long id) {
        return IMResult.ok(imUserFriendService.detail(id));
    }
}
