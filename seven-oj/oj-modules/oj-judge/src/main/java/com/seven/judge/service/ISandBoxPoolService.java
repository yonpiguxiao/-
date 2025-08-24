package com.seven.judge.service;

import com.seven.judge.domain.SandBoxExecuteResult;

import java.util.List;

public interface ISandBoxPoolService {
    SandBoxExecuteResult exeJavaCode(Long userId, String userCode, List<String> inputList);

}
