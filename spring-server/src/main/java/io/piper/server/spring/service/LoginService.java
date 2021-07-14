package io.piper.server.spring.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import io.piper.common.constant.Constants;
import io.piper.common.exception.IMErrorEnum;
import io.piper.common.exception.IMException;
import io.piper.common.pojo.dto.UserBasicDTO;
import io.piper.common.util.JwtTokenUtil;
import io.piper.server.spring.dto.LoginDTO;
import io.piper.server.spring.pojo.entity.ImUser;
import io.piper.server.spring.pojo.mapper.ImUserMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Service
public class LoginService {

    @Value("${spring.profiles.active}")
    private String profile;

    @Resource
    private ImUserMapper imUserMapper;

    @Resource
    private RedisTemplate redisTemplate;

    public void login(HttpServletRequest req, LoginDTO dto) {
        Long uid = dto.getUid();
        String pwd = dto.getPwd();
        String clientType = req.getHeader("clientType");
        if (Objects.isNull(uid) || Strings.isBlank(pwd)) {
            throw IMException.build(IMErrorEnum.USER_NOT_FOUND);
        }

        ImUser user = imUserMapper.selectByPrimaryKey(uid);
        if (Objects.isNull(user)) {
            throw IMException.build(IMErrorEnum.USER_NOT_FOUND);
        }

        String md5Hex = DigestUtil.md5Hex(user.getSalt() + pwd);
        if (!md5Hex.equals(user.getPassword())) {
            throw IMException.build(IMErrorEnum.INVALID_PWD);
        }

        // web-token - userBasicInfo
        // android-token - userBasicInfo
        // uid -> [web-token,android-token,ios-token]

        UserBasicDTO userBasicDTO = new UserBasicDTO();
        userBasicDTO.setId(user.getId());
        userBasicDTO.setNickname(user.getNickname());
        userBasicDTO.setClientType(clientType);
        String token = IdUtil.fastSimpleUUID();

        HttpSession session = req.getSession();
        session.setAttribute("token", token);

        if (!"local".equals(profile)) {
            redisTemplate.opsForValue().set(Constants.USER_TOKEN + token, userBasicDTO, JwtTokenUtil.EXPIRE_HOUR * 3600);
            redisTemplate.opsForHash().put(Constants.USER_TOKEN_CLIENT + user.getId(), clientType, token);
        }
    }

    public void logout(HttpServletRequest req) {
        try {
            req.logout();
        } catch (ServletException e) {
            throw IMException.build(IMErrorEnum.SERVER_ERROR);
        }
    }
}
