package com.zxz.server.utils;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//Token工具类
@Component
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;


    //根据用户信息生成Token
    public String generateToken(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }


    //根据荷载生成JWT Token
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.ES512, secret)
                .setExpiration(generateExpirationDate())
                .compact();
    }

    //生成Token的失效时间
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    //从Token中获取用户名
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    //从Token中获取荷载
    private Claims getClaimsFromToken(String token) {
        Claims body = null;
        try {
            body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body;
    }

    //判断Token是否有效
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    //判断token是否失效的具体方法
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDataFromToken(token);
        return expiredDate.before(new Date());
    }

    //获取token失效时间
    private Date getExpiredDataFromToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        return claimsFromToken.getExpiration();
    }

    //判断token是否可以被刷新
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    //刷新Token
    public String refreshToken(String token){
        Claims claimsFromToken = getClaimsFromToken(token);
        claimsFromToken.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claimsFromToken);
    }


}
