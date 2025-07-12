package com.seven.system.service;

import com.seven.common.core.domain.R;
import com.seven.system.controller.LoginResult;

public interface ISysUserService {
    R<Void> login(String userAccount, String password);
}
