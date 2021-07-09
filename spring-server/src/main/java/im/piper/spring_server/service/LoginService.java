package im.piper.spring_server.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import im.piper.spring_server.pojo.entity.User;
import im.piper.spring_server.pojo.entity.UserExample;
import im.piper.spring_server.pojo.mapper.UserMapper;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import piper.im.common.constant.Constants;
import piper.im.common.exception.IMErrorEnum;
import piper.im.common.exception.IMException;
import piper.im.common.pojo.dto.UserBasicDTO;
import piper.im.common.util.JwtTokenUtil;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Service
public class LoginService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RedisTemplate redisTemplate;

    public void login(HttpServletRequest req, String uid, String pwd, String clientType) {
        if (Objects.isNull(uid) || Strings.isBlank(pwd)) {
            throw IMException.build(IMErrorEnum.USER_NOT_FOUND);
        }

        UserExample example = new UserExample();
        example.createCriteria().andIdEqualTo(Long.valueOf(uid));
        List<User> users = userMapper.selectByExample(example);
        if (CollectionUtil.isEmpty(users)) {
            throw IMException.build(IMErrorEnum.USER_NOT_FOUND);
        }
        User user = users.get(0);

        String md5Hex = DigestUtil.md5Hex(user.getSalt() + pwd);
        if (!md5Hex.equals(user.getPassword())) {
            throw IMException.build(IMErrorEnum.INVALID_PWD);
        }

        // web-token - userBasicInfo
        // android-token - userBasicInfo
        // uid -> [web-token,android-token,ios-token]

        UserBasicDTO userBasicDTO = new UserBasicDTO();
        userBasicDTO.setId(user.getId().toString());
        userBasicDTO.setNickname(user.getNickname());
        userBasicDTO.setClientType(clientType);

        String token = IdUtil.fastSimpleUUID();

        redisTemplate.opsForValue().set(Constants.USER_TOKEN + token, JSONUtil.toJsonStr(userBasicDTO), JwtTokenUtil.EXPIRE_HOUR * 3600);
        redisTemplate.opsForHash().put(Constants.USER_TOKEN_CLIENT + user.getId(), clientType, token);

        HttpSession session = req.getSession();
        session.setAttribute("token", token);
    }

    public void logout(HttpServletRequest req) {
        try {
            req.logout();
        } catch (ServletException e) {
            throw IMException.build(IMErrorEnum.SERVER_ERROR);
        }
    }
}