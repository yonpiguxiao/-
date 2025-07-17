package com.seven.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.seven.common.core.domain.R;
import com.seven.common.core.enums.ResultCode;
import com.seven.common.security.utils.JwtUtils;
import com.seven.system.domain.SysUser;
import com.seven.system.mapper.SysUserMapper;
import com.seven.system.service.ISysUserService;
import com.seven.system.utils.BCryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public R<Void> login(String userAccount, String password) {
        //通过账号查询,对应的用户信息
        SysUser sysUser = sysUserMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .select(SysUser::getPassword)
                .eq(SysUser::getUserAccount, userAccount));
        if(sysUser == null) {
            return R.fail(ResultCode.FAILED_USER_NOT_EXISTS);
        }
        if(BCryptUtils.matchesPassword(password, sysUser.getPassword())) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", sysUser.getUserId());
            JwtUtils.createToken(claims, );
            return R.ok();
        }
        return R.fail(ResultCode.FAILED_LOGIN);
    }
}
