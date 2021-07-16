/*
 * Copyright 2020 The PiperChat
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
package io.piper.server.spring.config;

import io.piper.common.constant.Constants;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.dto.UserTokenDTO;
import io.piper.server.spring.service.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${spring.profiles.active}")
    private String profile;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Object objToken = request.getHeader("token");
        if (Objects.isNull(objToken)) {
            throw new IMException(IMErrorEnum.INVALID_TOKEN);
        }
        String token = String.valueOf(objToken);
        if ("local".equals(profile)) {
            boolean exToken = LoginService.USER_TOKENS.containsKey(Constants.USER_TOKEN + token);
            if (!exToken) {
                throw new IMException(IMErrorEnum.INVALID_TOKEN);
            }
        } else {
            UserTokenDTO dto = (UserTokenDTO) redisTemplate.opsForValue().get(Constants.USER_TOKEN + token);
            if (Objects.isNull(dto)) {
                throw new IMException(IMErrorEnum.INVALID_TOKEN);
            }
        }
        return true;
    }
}
