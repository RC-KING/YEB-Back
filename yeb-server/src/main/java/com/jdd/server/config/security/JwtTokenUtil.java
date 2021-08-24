package com.jdd.server.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
@Component
public class JwtTokenUtil {
    public static final String CLAIM_KEY_USERNAME = "sub";
    public static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    public String secret;
    @Value("${jwt.expiration}")
    public Long expiration;


    // ==========================外部调用方法 1================================= //
    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
    /**
     * 根据荷载生成Token
     */
    private String generateToken(Map<String, Object> claims) {
        // JWT创建Token
        return Jwts.builder()
                .setClaims(claims)
                // 这里的时间需要经过处理 Long==>Date()
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    /**
     * 生成token过期时间
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    // ==========================外部调用方法 2================================= //
    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFormToken(String token) {
        String username;
        try {
            // 从荷载中获取用户名
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            // 若出了异常,则设置用户名为空
            username = null;
        }
        return username;
    }
    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            // JWT解析Token
            claims = Jwts.parser()
                    // 需要密码
                    .setSigningKey(secret)
                    // 需要Token
                    .parseClaimsJws(token)
                    // 获取荷载
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }


    // ==========================外部调用方法 3================================= //
    /**
     * 验证token是否有效
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFormToken(token);
        // 判断用户名是否相同,以及Token是否过期
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    /**
     * 判断token是否失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        // 从荷载中获取过期时间
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    // =================================================================== //
    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    // =================================================================== //
    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        // 获取荷载
        Claims claims = getClaimsFromToken(token);
        // 将当前时间设置为刷新时间
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }
}
