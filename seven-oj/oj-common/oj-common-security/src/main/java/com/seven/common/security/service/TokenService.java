package com.seven.common.security.service;

import cn.hutool.core.lang.UUID;
import com.seven.common.core.constants.CacheConstants;
import com.seven.common.core.constants.JwtConstants;
import com.seven.common.redis.service.RedisService;
import com.seven.common.core.domain.LoginUser;
import com.seven.common.core.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class TokenService {

    @Autowired
    private RedisService redisService;

    public String createToken(Long userId, String secret, Integer identity) {
        Map<String, Object> claims = new HashMap<>();
        String userKey = UUID.fastUUID().toString();
        claims.put(JwtConstants.LOGIN_USER_ID, userId);
        claims.put(JwtConstants.LOGIN_USER_KEY, userKey);
        String token = JwtUtils.createToken(claims, secret);
        String key = CacheConstants.LOGIN_TOKEN_KEY + userKey;
        LoginUser loginUser = new LoginUser();
        loginUser.setIdentity(identity);
        redisService.setCacheObject(key, loginUser, CacheConstants.EXP, TimeUnit.MINUTES);
        return token;
    }

    public void extendToken(String token) {

    }
}
