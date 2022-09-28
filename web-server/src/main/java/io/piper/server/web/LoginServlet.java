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
package io.piper.server.web;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Singleton;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.piper.common.constant.Constants;
import io.piper.common.db.RedisDS;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.pojo.entity.ImUser;
import io.piper.common.util.IdUtil;
import io.piper.common.util.StringUtil;
import io.piper.server.web.repository.dao.UserDAO;
import io.piper.server.web.repository.impl.UserDAOJdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);
    private static final UserDAO userDAO = Singleton.get(UserDAOJdbc.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String body = IoUtil.read(req.getReader());
        log.info("login body:{}", body);
        JSONObject reqBody = JSON.parseObject(body);
        this.login(req, reqBody.getString("uid"), reqBody.getString("pwd"), req.getHeader("clientType"));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        this.logout(req);
    }

    private void login(HttpServletRequest req, String uid, String pwd, String clientType) {
        if (StringUtil.isAnyEmpty(uid, pwd)) {
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

        UserTokenDTO tokenDTO = new UserTokenDTO();
        tokenDTO.setId(imUser.getId());
        tokenDTO.setNickname(imUser.getNickname());
        tokenDTO.setClientName(clientType);

        String token = IdUtil.fastSimpleUUID();
        Jedis jedis = RedisDS.getJedis();
        jedis.set(Constants.USER_TOKEN + token, JSON.toJSONString(tokenDTO), SetParams.setParams().ex(43200L));
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
