package com.seven.friend.domain.exam.dto;

import com.seven.common.core.domain.PageQueryDTO;
import lombok.Data;

@Data
public class ExamQueryDTO extends PageQueryDTO {
    private String title;

    private String startTime;

    private String endTime;

    private Integer type; //0: 未完赛; 1: 历史竞赛;

    private Integer status;
}
