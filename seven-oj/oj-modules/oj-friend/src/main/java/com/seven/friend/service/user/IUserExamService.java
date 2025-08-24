package com.seven.friend.service.user;

import com.seven.common.core.domain.TableDataInfo;
import com.seven.friend.domain.exam.dto.ExamQueryDTO;

public interface IUserExamService {
    int enter(String token, Long examId);

    TableDataInfo list(ExamQueryDTO examQueryDTO);
}
