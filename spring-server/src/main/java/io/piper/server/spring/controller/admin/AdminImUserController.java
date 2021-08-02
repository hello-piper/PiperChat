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
import io.piper.server.spring.dto.ImUserDTO;
import io.piper.server.spring.dto.PageVO;
import io.piper.server.spring.dto.page_dto.ImUserPageDTO;
import io.piper.server.spring.service.ImUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "后台 用户管理")
@RequestMapping("admin/im-user")
public class AdminImUserController {

    @Resource
    private ImUserService imUserService;

    @PostMapping("/page")
    @ApiOperation("用户管理 分页")
    public IMResult<PageVO<ImUserDTO>> page(@RequestHeader("token") String token, @RequestBody ImUserPageDTO pageDTO) {
        return IMResult.ok(imUserService.page(pageDTO));
    }

    @PostMapping("/add")
    @ApiOperation("用户管理 添加")
    public IMResult<Boolean> add(@RequestHeader("token") String token, @RequestBody ImUserDTO dto) {
        return IMResult.ok(imUserService.add(dto, LoginUserHolder.get()));
    }

    @PutMapping("/update")
    @ApiOperation("用户管理 修改")
    public IMResult<Boolean> update(@RequestHeader("token") String token, @RequestBody ImUserDTO dto) {
        return IMResult.ok(imUserService.update(dto, LoginUserHolder.get()));
    }

    @DeleteMapping("/delete")
    @ApiOperation("用户管理 删除")
    public IMResult<Boolean> delete(@RequestHeader("token") String token, @RequestParam Long id) {
        return IMResult.ok(imUserService.delete(id, LoginUserHolder.get()));
    }

    @GetMapping("/detail")
    @ApiOperation("用户管理 详情")
    public IMResult<ImUserDTO> detail(@RequestHeader("token") String token, @RequestParam Long id) {
        return IMResult.ok(imUserService.detail(id));
    }
}
