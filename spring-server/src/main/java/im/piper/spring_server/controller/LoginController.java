package im.piper.spring_server.controller;

import im.piper.spring_server.dto.LoginDTO;
import im.piper.spring_server.service.LoginService;
import org.springframework.web.bind.annotation.*;

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
