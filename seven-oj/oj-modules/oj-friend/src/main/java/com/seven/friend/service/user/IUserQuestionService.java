package com.seven.friend.service.user;

import com.seven.common.core.domain.R;
import com.seven.friend.domain.user.dto.UserSubmitDTO;
import com.seven.api.domain.vo.UserQuestionResultVO;

public interface IUserQuestionService {
    R<UserQuestionResultVO> submit(UserSubmitDTO userSubmitDTO);

    boolean rabbitSubmit(UserSubmitDTO userSubmitDTO);

    UserQuestionResultVO exeResult(Long examId, Long questionId, String currentTime);
}
