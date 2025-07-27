package com.seven.system.service.sysuser;

import com.seven.common.core.domain.R;
import com.seven.common.core.domain.vo.LoginUserVO;
import com.seven.system.domain.sysuser.dto.SysUserSaveDTO;

public interface ISysUserService {
    R<String> login(String userAccount, String password);

    boolean logout(String token);

    int add(SysUserSaveDTO sysUserSaveDTO);

    R<LoginUserVO> info(String token);

}
