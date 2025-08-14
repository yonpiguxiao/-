package com.seven.friend.service.user;

import com.seven.common.core.domain.R;
import com.seven.common.core.domain.vo.LoginUserVO;
import com.seven.friend.domain.user.dto.UserCodeDTO;

public interface IUserService {
    boolean sendCode(UserCodeDTO userCodeDTO);

    String codeLogin(String phone, String code);

    boolean logout(String token);

    R<LoginUserVO> info(String token);
}
