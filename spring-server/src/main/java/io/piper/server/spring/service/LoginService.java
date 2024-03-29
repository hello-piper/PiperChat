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
package io.piper.server.spring.service;

import io.piper.common.constant.Constants;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.util.*;
import io.piper.server.spring.dto.ImUserDTO;
import io.piper.server.spring.dto.LoginDTO;
import io.piper.server.spring.dto.LoginVO;
import io.piper.server.spring.pojo.entity.ImUser;
import io.piper.server.spring.pojo.entity.ImUserAdmin;
import io.piper.server.spring.pojo.entity.ImUserExample;
import io.piper.server.spring.pojo.mapper.ImUserAdminMapper;
import io.piper.server.spring.pojo.mapper.ImUserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {

    @Value("${spring.profiles.active}")
    private String profile;

    @Resource
    private ImUserMapper imUserMapper;

    @Resource
    private ImUserAdminMapper imUserAdminMapper;

    @Resource
    private Snowflake snowflake;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public LoginVO login(HttpServletRequest req, LoginDTO dto) {
        String code = dto.getVerifyCode();
        String oldCode = this.getCodeByCookie(req, "verifyCode");
        if (StringUtil.isAnyEmpty(code, oldCode) || !code.equalsIgnoreCase(oldCode)) {
//            throw IMException.build(IMErrorEnum.INVALID_VERIFY_CODE);
        }

        String email = dto.getEmail();
        String pwd = dto.getPwd();
        String clientType = req.getHeader("clientType");
        if (StringUtil.isAnyEmpty(email, pwd)) {
            throw IMException.build(IMErrorEnum.PARAM_ERROR);
        }

        ImUser user;
        ImUserExample userExample = new ImUserExample();
        userExample.createCriteria().andEmailEqualTo(email);
        List<ImUser> imUsers = imUserMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(imUsers)) {
            // register
            user = new ImUser();
            user.setId(snowflake.nextId());
            user.setEmail(email);
            user.setNickname("萌爆的小鹬" + ThreadLocalRandom.current().nextInt(3));
            user.setAvatar("https://profile.csdnimg.cn/6/C/F/1_gy325416");
            user.setSalt(RandomUtil.randomString(5));
            user.setPassword(DigestUtil.md5Hex(user.getSalt() + pwd));
            user.setCreateTime(System.currentTimeMillis());
            int row = imUserMapper.insertSelective(user);
            if (row != 1) {
                throw IMException.build(IMErrorEnum.SERVER_ERROR);
            }
        } else {
            // login
            user = imUsers.get(0);
        }

        String md5Hex = DigestUtil.md5Hex(user.getSalt() + pwd);
        if (!md5Hex.equals(user.getPassword())) {
            throw IMException.build(IMErrorEnum.INVALID_PWD);
        }

        UserTokenDTO tokenDTO = new UserTokenDTO();
        tokenDTO.setId(user.getId());
        tokenDTO.setNickname(user.getNickname());
        tokenDTO.setAvatar(user.getAvatar());
        tokenDTO.setEmail(user.getEmail());
        tokenDTO.setPhone(user.getPhone());
        tokenDTO.setClientName(clientType);
        tokenDTO.setIsAdmin(false);
        ImUserAdmin imUserAdmin = imUserAdminMapper.selectByPrimaryKey(user.getId());
        if (!Objects.isNull(imUserAdmin)) {
            tokenDTO.setIsAdmin(true);
        }

        String token = IdUtil.fastSimpleUUID();
        redisTemplate.opsForValue().set(Constants.USER_TOKEN + token, tokenDTO, 12L, TimeUnit.HOURS);

        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        ImUserDTO userDTO = new ImUserDTO();
        BeanUtils.copyProperties(user, userDTO);
        loginVO.setUser(userDTO);
        return loginVO;
    }

    public void logout(HttpServletRequest req) {
        try {
            req.logout();
        } catch (ServletException e) {
            throw IMException.build(IMErrorEnum.SERVER_ERROR);
        }
    }

    public void verifyCode(HttpServletRequest req, HttpServletResponse resp) {
        VerifyCode verifyCode = new VerifyCode();
        BufferedImage image = verifyCode.getImage();
        resp.addCookie(new Cookie("verifyCode", verifyCode.getText()));
        try {
            verifyCode.output(image, resp.getOutputStream());
        } catch (IOException e) {
            throw IMException.build(IMErrorEnum.SERVER_ERROR);
        }
    }

    private String getCodeByCookie(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if (null == cookies) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (Objects.equals(name, cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
