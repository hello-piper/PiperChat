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
import io.piper.server.spring.dto.ImGroupDTO;
import io.piper.server.spring.service.ImGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "后台 群组管理")
@RequestMapping("admin/im-group")
public class AdminImGroupController {

    @Resource
    private ImGroupService imGroupService;

    @GetMapping("/page")
    @ApiOperation("群组管理 分页")
    public IMResult<PageImpl<ImGroupDTO>> page(
            @RequestHeader("token") String token,
            @ApiParam("页码数") @RequestParam Integer pageNum,
            @ApiParam("每页条数") @RequestParam Integer pageSize) {
        return IMResult.ok(imGroupService.page(pageNum, pageSize));
    }

    @PostMapping("/add")
    @ApiOperation("群组管理 添加")
    public IMResult<Boolean> add(@RequestHeader("token") String token, @RequestBody ImGroupDTO dto) {
        return IMResult.ok(imGroupService.add(dto, LoginUserHolder.get()));
    }

    @PutMapping("/update")
    @ApiOperation("群组管理 修改")
    public IMResult<Boolean> update(@RequestHeader("token") String token, @RequestBody ImGroupDTO dto) {
        return IMResult.ok(imGroupService.update(dto, LoginUserHolder.get()));
    }

    @DeleteMapping("/delete")
    @ApiOperation("群组管理 删除")
    public IMResult<Boolean> delete(@RequestHeader("token") String token, @RequestParam Long id) {
        return IMResult.ok(imGroupService.delete(id, LoginUserHolder.get()));
    }

    @GetMapping("/detail")
    @ApiOperation("群组管理 详情")
    public IMResult<ImGroupDTO> detail(@RequestHeader("token") String token, @RequestParam Long id) {
        return IMResult.ok(imGroupService.detail(id));
    }
}
