package com.seven.common.security.interceptor;

import cn.hutool.core.util.StrUtil;
import com.seven.common.core.constants.Constants;
import com.seven.common.core.constants.HttpConstants;
import com.seven.common.core.utils.ThreadLocalUtil;
import com.seven.common.security.service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getToken(request);
        if(StrUtil.isEmpty(token)) {
            return true;
        }
        Claims claims = tokenService.getClaims(token, secret);
        Long userId = tokenService.getUserId(claims);
        String userKey = tokenService.getUserKey(claims);
        ThreadLocalUtil.set(Constants.USER_ID, userId);
        ThreadLocalUtil.set(Constants.USER_KEY, userKey);
        tokenService.extendToken(claims);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(HttpConstants.AUTHENTICATION);
        if(StrUtil.isNotEmpty(token) && token.startsWith(HttpConstants.PREFIX)) {
            token = token.replaceFirst(HttpConstants.PREFIX, "");
        }
        return token;
    }
}
