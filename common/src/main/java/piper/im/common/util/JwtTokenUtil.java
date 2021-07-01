package piper.im.common.util;

import io.jsonwebtoken.*;
import piper.im.common.pojo.config.JwtProperties;

import java.util.Date;
import java.util.Map;

/**
 * jwt token工具类
 * <p>
 * jwt的claim里一般包含以下几种数据:
 * 1. iss -- token的发行者
 * 2. sub -- 该JWT所面向的用户
 * 3. aud -- 接收该JWT的一方
 * 4. exp -- token的失效时间
 * 5. nbf -- 在此时间段之前,不会被处理
 * 6. iat -- jwt发布时间
 * 7. jti -- jwt唯一标识,防止重复使用
 *
 * @author piper
 * @date 2020-08-01 15:17
 */
public class JwtTokenUtil {
    public static String SECRET;
    public static Long EXPIRE_HOUR;

    static {
        JwtProperties properties = YamlUtil.getConfig("jwt", JwtProperties.class);
        SECRET = properties.getSecret();
        EXPIRE_HOUR = properties.getExpireHour();
    }

    public JwtTokenUtil(JwtProperties properties) {
        SECRET = properties.getSecret();
        EXPIRE_HOUR = properties.getExpireHour();
    }

    public JwtTokenUtil(String secret, Long expireHour) {
        SECRET = secret;
        EXPIRE_HOUR = expireHour;
    }

    /**
     * 获取uid从token中
     */
    public static String getUidFromToken(String token) {
        return getClaimFromToken(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token).getIssuedAt();
    }

    /**
     * 获取jwt失效时间
     */
    public static Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token).getExpiration();
    }

    /**
     * 获取jwt接收者
     */
    public static String getAudienceFromToken(String token) {
        return getClaimFromToken(token).getAudience();
    }

    /**
     * 获取私有的jwt claim
     */
    public static String getPrivateClaimFromToken(String token, String key) {
        return getClaimFromToken(token).get(key).toString();
    }

    /**
     * 获取jwt的payload部分
     */
    public static Claims getClaimFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 解析token是否正确(true-正确, false-错误)
     */
    public static Boolean checkToken(String token) throws JwtException {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * 验证token是否失效
     * true:过期   false:没过期
     */
    public static Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }

    /**
     * 生成token,根据uid和默认过期时间
     */
    public static String generateToken(String uid, Map<String, Object> claims) {
        final Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_HOUR * 60 * 60 * 1000);
        return generateToken(uid, expirationDate, claims);
    }

    /**
     * 生成token,根据uid和过期时间
     */
    public static String generateToken(String uid, Date expireDate, Map<String, Object> claims) {
        final Date createdDate = new Date();
        if (claims == null) {
            return Jwts.builder()
                    .setSubject(uid)
                    .setIssuedAt(createdDate)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS256, SECRET)
                    .compact();
        } else {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(uid)
                    .setIssuedAt(createdDate)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS256, SECRET)
                    .compact();
        }
    }
}
