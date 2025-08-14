package com.seven.system.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.system.domain.user.User;
import com.seven.system.domain.user.dto.UserQueryDTO;
import com.seven.system.domain.user.vo.UserVO;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<UserVO> selectUserList(UserQueryDTO userQueryDTO);
}
