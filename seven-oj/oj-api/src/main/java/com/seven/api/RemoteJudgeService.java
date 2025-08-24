package com.seven.api;


import com.seven.common.core.constants.Constants;
import com.seven.common.core.domain.R;
import com.seven.api.domain.dto.JudgeSubmitDTO;
import com.seven.api.domain.vo.UserQuestionResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId = "RemoteJudgeService", value = Constants.JUDGE_SERVICE)
public interface RemoteJudgeService {

    @PostMapping("/judge/doJudgeJavaCode")
    R<UserQuestionResultVO> doJudgeJavaCode(@RequestBody JudgeSubmitDTO judgeSubmitDTO);
}
