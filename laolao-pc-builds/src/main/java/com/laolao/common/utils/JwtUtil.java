package com.laolao.common.utils;

import com.laolao.common.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
    @Resource
    private JwtProperties jwtProperties;

    private String getSecretKey() {
        return jwtProperties.getUserSecretKey();
    }

    private long getTtlMillis() {
        return jwtProperties.getUserTtl();
    }

    /**
     * 生成jwt
     * @param claims 内容
     * @return jwt令牌
     */
    public String createJWT(Map<String, Object> claims) {
        // 设置过期时间
        Date exp = new Date(System.currentTimeMillis() + getTtlMillis());

        return Jwts.builder()
                .claims(claims)
                .signWith(Keys.hmacShaKeyFor(getSecretKey().getBytes()))
                .expiration(exp)
                .compact();
    }

    /**
     * jwt解密
     * @param jwt jwt令牌
     * @return {userId=1, username=laolao, exp=1760444574}
     */
    public Claims parseJWT(String jwt) {

        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(getSecretKey().getBytes()))
                .build().parseSignedClaims(jwt).getPayload();
    }
}
