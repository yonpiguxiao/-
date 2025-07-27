package com.seven.system.service.question;

import com.seven.common.core.domain.TableDataInfo;
import com.seven.system.domain.question.dto.QuestionQueryDTO;
import com.seven.system.domain.question.vo.QuestionVO;
import java.util.List;

public interface IQuestionService {
    List<QuestionVO> list(QuestionQueryDTO questionQueryDTO);
}
