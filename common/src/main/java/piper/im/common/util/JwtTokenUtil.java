package piper.im.common.util;

import io.jsonwebtoken.*;

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

    /**
     * jwt的秘钥
     */
    private String jwtSecret = "123456";

    /**
     * 默认jwt的过期时间(小时)
     */
    private Long defaultExpiredDate = 3L;

    public JwtTokenUtil() {
    }

    public JwtTokenUtil(String jwtSecret, Long defaultExpiredDate) {
        this.jwtSecret = jwtSecret;
        this.defaultExpiredDate = defaultExpiredDate;
    }

    /**
     * 获取uid从token中
     */
    public String getUidFromToken(String token) {
        return getClaimFromToken(token).getSubject();
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token).getIssuedAt();
    }

    /**
     * 获取jwt失效时间
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token).getExpiration();
    }

    /**
     * 获取jwt接收者
     */
    public String getAudienceFromToken(String token) {
        return getClaimFromToken(token).getAudience();
    }

    /**
     * 获取私有的jwt claim
     */
    public String getPrivateClaimFromToken(String token, String key) {
        return getClaimFromToken(token).get(key).toString();
    }

    /**
     * 获取jwt的payload部分
     */
    public Claims getClaimFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 解析token是否正确(true-正确, false-错误)
     */
    public Boolean checkToken(String token) throws JwtException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * 验证token是否失效
     * true:过期   false:没过期
     */
    public Boolean isTokenExpired(String token) {
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
    public String generateToken(String uid, Map<String, Object> claims) {
        final Date expirationDate = new Date(System.currentTimeMillis() + defaultExpiredDate * 60 * 60 * 1000);
        return generateToken(uid, expirationDate, claims);
    }

    /**
     * 生成token,根据uid和过期时间
     */
    public String generateToken(String uid, Date expireDate, Map<String, Object> claims) {
        final Date createdDate = new Date();
        if (claims == null) {
            return Jwts.builder()
                    .setSubject(uid)
                    .setIssuedAt(createdDate)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
                    .compact();
        } else {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(uid)
                    .setIssuedAt(createdDate)
                    .setExpiration(expireDate)
                    .signWith(SignatureAlgorithm.HS512, this.jwtSecret)
                    .compact();
        }
    }
}