package com.seven.system.service.user;

import com.seven.system.domain.user.dto.UserQueryDTO;
import com.seven.system.domain.user.dto.UserUpdateDTO;
import com.seven.system.domain.user.vo.UserVO;

import java.util.List;

public interface IUserService {
    List<UserVO> list(UserQueryDTO userQueryDTO);

    int updateStatus(UserUpdateDTO userUpdateDTO);
}
