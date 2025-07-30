package com.seven.system.domain.question.dto;

import lombok.Data;

@Data
public class QuestionAddDTO {
    private String title;

    private Integer difficulty;

    private Long timeLimit;

    private Long spaceLimit;

    private String content;

    private String questionCase;

    private String defaultCode;

    private String mainFunc;
}
