package com.seven.friend.domain.question.dto;

import com.seven.common.core.domain.PageQueryDTO;
import lombok.Data;

@Data
public class QuestionQueryDTO extends PageQueryDTO {
    private String keyWord;

    private Integer difficulty;
}
