package piper.im.spring_server.controller;

import org.springframework.web.bind.annotation.*;
import piper.im.spring_server.dto.LoginDTO;
import piper.im.spring_server.service.LoginService;

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
