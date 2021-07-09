package im.piper.spring_server.controller;

import im.piper.spring_server.service.LoginService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping
    public void login(HttpServletRequest req) {
        String uid = req.getParameter("uid");
        String pwd = req.getParameter("pwd");
        String clientType = req.getHeader("clientType");
        loginService.login(req, uid, pwd, clientType);
    }

    @DeleteMapping
    public void logout(HttpServletRequest req) {
        loginService.logout(req);
    }
}
