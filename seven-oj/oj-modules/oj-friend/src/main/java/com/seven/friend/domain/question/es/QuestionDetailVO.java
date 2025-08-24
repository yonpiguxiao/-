package com.seven.friend.domain.question.es;

import com.seven.friend.domain.question.vo.QuestionVO;
import lombok.Data;

@Data
public class QuestionDetailVO extends QuestionVO {
    private Long timeLimit;

    private Long spaceLimit;

    private String content;

    private String defaultCode;
}
