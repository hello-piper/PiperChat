package io.piper.server.spring.controller;

import org.springframework.web.bind.annotation.*;
import io.piper.server.spring.dto.LoginDTO;
import io.piper.server.spring.service.LoginService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping
    public void login(HttpServletRequest req, @RequestBody LoginDTO dto) {
        loginService.login(req, dto);
    }

    @DeleteMapping
    public void logout(HttpServletRequest req) {
        loginService.logout(req);
    }
}
