package com.seven.system.service;

import com.seven.common.core.domain.R;
import com.seven.system.domain.SysUserSaveDTO;

public interface ISysUserService {
    R<String> login(String userAccount, String password);

    int add(SysUserSaveDTO sysUserSaveDTO);
}
