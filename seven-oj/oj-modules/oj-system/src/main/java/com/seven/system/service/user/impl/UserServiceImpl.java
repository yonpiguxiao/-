package com.seven.system.service.user.impl;

import com.github.pagehelper.PageHelper;
import com.seven.common.core.enums.ResultCode;
import com.seven.common.security.exception.ServiceException;
import com.seven.system.domain.user.User;
import com.seven.system.domain.user.dto.UserQueryDTO;
import com.seven.system.domain.user.dto.UserUpdateDTO;
import com.seven.system.domain.user.vo.UserVO;
import com.seven.system.manager.UserCacheManager;
import com.seven.system.mapper.user.UserMapper;
import com.seven.system.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserCacheManager userCacheManager;

    @Override
    public List<UserVO> list(UserQueryDTO userQueryDTO) {
        PageHelper.startPage(userQueryDTO.getPageNum(), userQueryDTO.getPageSize());
        return userMapper.selectUserList(userQueryDTO);
    }

    @Override
    public int updateStatus(UserUpdateDTO userUpdateDTO) {
        User user = userMapper.selectById(userUpdateDTO.getUserId());
        if(user == null) {
            throw new ServiceException(ResultCode.FAILED_USER_NOT_EXISTS);
        }
        user.setStatus(userUpdateDTO.getStatus());
        userCacheManager.updateStatus(user.getUserId(), userUpdateDTO.getStatus());
        return userMapper.updateById(user);
    }
}
