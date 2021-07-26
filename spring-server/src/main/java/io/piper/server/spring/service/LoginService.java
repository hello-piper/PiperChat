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

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.piper.common.constant.Constants;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.common.util.LRUCache;
import io.piper.server.spring.dto.LoginDTO;
import io.piper.server.spring.pojo.entity.ImUser;
import io.piper.server.spring.pojo.entity.ImUserExample;
import io.piper.server.spring.pojo.mapper.ImUserMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService {

    @Value("${spring.profiles.active}")
    private String profile;

    @Resource
    private ImUserMapper imUserMapper;

    @Resource
    private RedisTemplate redisTemplate;

    public static final Map<String, UserTokenDTO> USER_TOKENS = Collections.synchronizedMap(new LRUCache<>(10));

    public String login(HttpServletRequest req, LoginDTO dto) {
        String email = dto.getEmail();
        String pwd = dto.getPwd();
        String clientType = req.getHeader("clientType");
        if (Strings.isBlank(email) || Strings.isBlank(pwd)) {
            throw IMException.build(IMErrorEnum.USER_NOT_FOUND);
        }

        ImUser user;
        ImUserExample userExample = new ImUserExample();
        userExample.createCriteria().andEmailEqualTo(email);
        List<ImUser> imUsers = imUserMapper.selectByExample(userExample);
        if (CollectionUtil.isEmpty(imUsers)) {
            // register
            user = new ImUser();
            user.setId(IdUtil.getSnowflake(0, 0).nextId());
            user.setEmail(email);
            user.setNickname("萌爆的小鹬" + RandomUtil.randomNumbers(3));
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
        tokenDTO.setPhone(user.getPhone());
        tokenDTO.setClientType(clientType);

        String token = IdUtil.fastSimpleUUID();

        if ("local".equals(profile)) {
            USER_TOKENS.put(Constants.USER_TOKEN + token, tokenDTO);
        } else {
            redisTemplate.opsForValue().set(Constants.USER_TOKEN + token, tokenDTO, 12L, TimeUnit.HOURS);
        }
        return token;
    }

    public void logout(HttpServletRequest req) {
        try {
            req.logout();
        } catch (ServletException e) {
            throw IMException.build(IMErrorEnum.SERVER_ERROR);
        }
    }
}
