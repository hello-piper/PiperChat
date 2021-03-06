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
import io.piper.server.spring.dto.LoginDTO;
import io.piper.server.spring.dto.LoginVO;
import io.piper.server.spring.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "登录相关接口")
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @GetMapping("/verify-code")
    @ApiOperation("获取图片验证码")
    public void verifyCode(HttpServletRequest req, HttpServletResponse resp) {
        loginService.verifyCode(req, resp);
    }

    @PostMapping("/login")
    @ApiOperation("登录/注册")
    public IMResult<LoginVO> login(HttpServletRequest req, @RequestBody LoginDTO dto) {
        return IMResult.ok(loginService.login(req, dto));
    }

    @DeleteMapping("/logout")
    @ApiOperation("退出登录")
    public void logout(HttpServletRequest req) {
        loginService.logout(req);
    }
}
