package com.seven.common.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    /**
     * ⽣成令牌
     *
     * @param claims 数据
     * @param secret 密钥
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims, String secret)
    {
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }
    /**
     * 从令牌中获取数据
     *
     * @param token 令牌
     * @param secret 密钥
     * @return 数据
     */
    public static Claims parseToken(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public static void main(String[] args) {
//        Map<String, Object> claim = new HashMap<>();
//        claim.put("userId", 123456789L);
//        System.out.println(createToken(claim, "ajdsojgijoiaoidijoiad"));
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjEyMzQ1Njc4OX0.AE6oTiG99wt1mNJ9aJdBkjBprYmLV2fqzArh2vNqHdns4IytNn1ZYtQmxX1KtVYjPO7Z1sWzRku0K1pHPzkUkw";
        Claims claim = parseToken(token, "ajdsojgijoiaoidijoiad");
        System.out.println(claim);
    }
}
