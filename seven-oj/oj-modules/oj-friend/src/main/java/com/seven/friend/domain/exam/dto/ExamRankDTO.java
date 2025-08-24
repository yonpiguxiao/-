package com.seven.friend.domain.exam.dto;

import com.seven.common.core.domain.PageQueryDTO;
import lombok.Data;

@Data
public class ExamRankDTO extends PageQueryDTO {
    private Long examId;
}
