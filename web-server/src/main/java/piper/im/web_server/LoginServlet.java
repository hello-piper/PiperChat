package piper.im.web_server;

import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import piper.im.common.dao.UserDAO;
import piper.im.common.exception.IMErrorEnum;
import piper.im.common.exception.IMException;
import piper.im.common.pojo.entity.User;
import piper.im.common.util.JwtTokenUtil;
import piper.im.repository.impl.UserDAOImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginServlet extends HttpServlet {
    private static Logger log = LogManager.getLogger(LoginServlet.class);
    UserDAO userDAO = new UserDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = IoUtil.read(req.getReader());
        log.info("login body:{}", body);
        JSONObject reqBody = JSONObject.parseObject(body);
        this.login(req, reqBody.getString("uid"), reqBody.getString("pwd"));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        this.logout(req);
    }

    private void login(HttpServletRequest req, String uid, String pwd) {
        if (Strings.isBlank(uid) || Strings.isBlank(pwd)) {
            throw IMException.build(IMErrorEnum.USER_NOT_FOUND);
        }

        User user = userDAO.getById(uid);
        if (Objects.isNull(user)) {
            throw IMException.build(IMErrorEnum.USER_NOT_FOUND);
        }

        String md5Hex = DigestUtil.md5Hex(user.getSalt() + pwd);
        if (!md5Hex.equals(user.getPassword())) {
            throw IMException.build(IMErrorEnum.INVALID_PWD);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("avatar", user.getAvatar());
        claims.put("nickname", user.getNickname());
        String token = JwtTokenUtil.generateToken(user.getId(), claims);

        HttpSession session = req.getSession();
        session.setAttribute("token", token);
    }

    private void logout(HttpServletRequest request) {
        request.getSession().removeAttribute("token");
    }
}
