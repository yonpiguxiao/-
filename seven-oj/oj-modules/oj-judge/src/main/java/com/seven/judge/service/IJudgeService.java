package com.seven.judge.service;

import com.seven.api.domain.dto.JudgeSubmitDTO;
import com.seven.api.domain.vo.UserQuestionResultVO;

public interface IJudgeService {
    UserQuestionResultVO doJudgeJavaCode(JudgeSubmitDTO judgeSubmitDTO);
}
