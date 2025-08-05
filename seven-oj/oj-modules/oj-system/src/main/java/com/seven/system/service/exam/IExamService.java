package com.seven.system.service.exam;

import com.seven.system.domain.exam.dto.ExamAddDTO;
import com.seven.system.domain.exam.dto.ExamQueryDTO;
import com.seven.system.domain.exam.dto.ExamQuestionAddDTO;
import com.seven.system.domain.exam.vo.ExamVO;

import java.util.List;

public interface IExamService {
    List<ExamVO> list(ExamQueryDTO examQueryDTO);

    int add(ExamAddDTO examAddDTO);

    boolean questionAdd(ExamQuestionAddDTO examQuestionAddDTO);
}
