package piper.im.web_server;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONObject;
import piper.im.common.util.JwtTokenUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = IoUtil.read(req.getReader());
        JSONObject bodyJson = JSONObject.parseObject(body);
        String uid = bodyJson.getString("uid");
        String password = bodyJson.getString("psd");

        // todo 用户检查

        // 生成token
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", "");
        claims.put("name", "");
        claims.put("phone", "");
        String token = JwtTokenUtil.generateToken("", claims);

        // 种植Token
        HttpSession session = req.getSession();
        session.setAttribute("token", token);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("token");
    }
}
