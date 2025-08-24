package com.seven.friend.service.question;

import com.seven.common.core.domain.TableDataInfo;
import com.seven.friend.domain.question.dto.QuestionQueryDTO;
import com.seven.friend.domain.question.es.QuestionDetailVO;
import com.seven.friend.domain.question.vo.QuestionVO;
import java.util.List;

public interface IQuestionService {
    TableDataInfo list(QuestionQueryDTO questionQueryDTO);

    QuestionDetailVO detail(Long questionId);

    String preQuestion(Long questionId);

    String nextQuestion(Long questionId);

    List<QuestionVO> hotList();
}
