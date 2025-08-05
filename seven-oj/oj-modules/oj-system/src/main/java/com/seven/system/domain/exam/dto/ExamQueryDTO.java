package com.seven.system.domain.exam.dto;

import com.seven.common.core.domain.PageQueryDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamQueryDTO extends PageQueryDTO {
    private String title;

    private String startTime;

    private String endTime;
}
