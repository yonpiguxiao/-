package com.seven.system.domain.question.dto;

import com.seven.common.core.domain.PageQueryDTO;
import lombok.Data;

@Data
public class QuestionQueryDTO extends PageQueryDTO {

    private String title;

    private Integer difficulty;

}
