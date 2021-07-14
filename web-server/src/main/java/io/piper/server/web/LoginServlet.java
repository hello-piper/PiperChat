package io.piper.server.web;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import io.piper.common.constant.Constants;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.dto.UserBasicDTO;
import io.piper.common.pojo.entity.ImUser;
import io.piper.common.util.JwtTokenUtil;
import io.piper.common.util.RedisDS;
import io.piper.server.web.repository.dao.UserDAO;
import io.piper.server.web.repository.impl.UserDAOJdbc;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * LoginServlet
 *
 * @author piper
 */
public class LoginServlet extends HttpServlet {
    private static final Logger log = LogManager.getLogger(LoginServlet.class);
    private static final UserDAO userDAO = Singleton.get(UserDAOJdbc.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = IoUtil.read(req.getReader());
        log.info("login body:{}", body);
        JSONObject reqBody = JSONUtil.parseObj(body);
        this.login(req, reqBody.getStr("uid"), reqBody.getStr("pwd"), req.getHeader("clientType"));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        this.logout(req);
    }

    private void login(HttpServletRequest req, String uid, String pwd, String clientType) {
        if (Strings.isBlank(uid) || Strings.isBlank(pwd)) {
            throw IMException.build(IMErrorEnum.USER_NOT_FOUND);
        }

        ImUser imUser = userDAO.getById(uid);
        if (Objects.isNull(imUser)) {
            throw IMException.build(IMErrorEnum.USER_NOT_FOUND);
        }

        String md5Hex = DigestUtil.md5Hex(imUser.getSalt() + pwd);
        if (!md5Hex.equals(imUser.getPassword())) {
            throw IMException.build(IMErrorEnum.INVALID_PWD);
        }

        // web-token - userBasicInfo
        // android-token - userBasicInfo
        // uid -> [web-token,android-token,ios-token]

        UserBasicDTO userBasicDTO = new UserBasicDTO();
        userBasicDTO.setId(imUser.getId());
        userBasicDTO.setNickname(imUser.getNickname());
        userBasicDTO.setClientType(clientType);

        String token = IdUtil.fastSimpleUUID();

        Jedis jedis = RedisDS.getJedis();
        jedis.set(Constants.USER_TOKEN + token, JSONUtil.toJsonStr(userBasicDTO),
                SetParams.setParams().ex(JwtTokenUtil.EXPIRE_HOUR * 3600));
        jedis.hset(Constants.USER_TOKEN_CLIENT + imUser.getId(), clientType, token);
        jedis.close();

        HttpSession session = req.getSession();
        session.setAttribute("token", token);
    }

    private void logout(HttpServletRequest request) {
        try {
            request.logout();
        } catch (ServletException e) {
            throw IMException.build(IMErrorEnum.SERVER_ERROR);
        }
    }
}
