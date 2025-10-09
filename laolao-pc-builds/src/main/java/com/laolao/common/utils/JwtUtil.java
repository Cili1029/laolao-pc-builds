package com.laolao.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 生成jwt
     * @param secretKey jwt秘钥
     * @param ttlMillis jwt过期时间(毫秒)
     * @param claims    内容
     * @return          jwt令牌
     */

    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {
        // 设置过期时间
        Date exp = new Date(System.currentTimeMillis() + ttlMillis);

        return Jwts.builder()
                .claims(claims)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .expiration(exp)
                .compact();
    }

    /**
     * jwt解密
     * @param secretKey jwt秘钥
     * @param jwt       jwt令牌
     * @return          {userId=1, username=laolao, exp=1760444574}
     */
    public static Claims parseJWT(String secretKey, String jwt) {

        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build().parseSignedClaims(jwt).getPayload();
    }
}
