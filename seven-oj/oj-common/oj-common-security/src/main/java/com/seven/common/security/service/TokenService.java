package com.seven.common.security.service;

import cn.hutool.core.lang.UUID;
import com.seven.common.core.constants.CacheConstants;
import com.seven.common.core.constants.JwtConstants;
import com.seven.common.redis.service.RedisService;
import com.seven.common.core.domain.LoginUser;
import com.seven.common.core.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
public class TokenService {

    @Autowired
    private RedisService redisService;

    public String createToken(Long userId, String secret, Integer identity) {
        Map<String, Object> claims = new HashMap<>();
        String userKey = UUID.fastUUID().toString();
        claims.put(JwtConstants.LOGIN_USER_ID, userId);
        claims.put(JwtConstants.LOGIN_USER_KEY, userKey);
        String token = JwtUtils.createToken(claims, secret);
        String tokenKey = getTokenKey(userKey);
        LoginUser loginUser = new LoginUser();
        loginUser.setIdentity(identity);
        redisService.setCacheObject(tokenKey, loginUser, CacheConstants.EXP, TimeUnit.MINUTES);
        return token;
    }

    public void extendToken(String token, String secret) {
        Claims claims;
        try {
            claims = JwtUtils.parseToken(token, secret); //获取令牌中信息 解析payload中信息
            if (claims == null) {
                log.error("解析token:{}, 出现异常", token);
                return;
            }
        } catch (Exception e) {
            log.error("解析token:{}, 出现异常:{}", token, e);
            return;
        }
        String userKey = JwtUtils.getUserKey(claims); //获取jwt中的key
        String tokenKey = getTokenKey(userKey);

        Long expire = redisService.getExpire(tokenKey, TimeUnit.MINUTES);
        if(expire != null && expire < CacheConstants.REFRESH_TIME) {
            redisService.expire(tokenKey, CacheConstants.EXP, TimeUnit.MINUTES);
        }

    }

    private String getTokenKey(String userKey) {
        return CacheConstants.LOGIN_TOKEN_KEY + userKey;
    }

}
