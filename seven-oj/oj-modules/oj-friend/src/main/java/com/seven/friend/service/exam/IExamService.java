package com.seven.friend.service.exam;

import com.seven.common.core.domain.TableDataInfo;
import com.seven.friend.domain.exam.dto.ExamQueryDTO;
import com.seven.friend.domain.exam.dto.ExamRankDTO;
import com.seven.friend.domain.exam.vo.ExamVO;

import java.util.List;

public interface IExamService {
    List<ExamVO> list(ExamQueryDTO examQueryDTO);

    TableDataInfo redisList(ExamQueryDTO examQueryDTO);

    String getFirstQuestion(Long examId);

    String preQuestion(Long examId, Long questionId);

    String nextQuestion(Long examId, Long questionId);

    TableDataInfo rankList(ExamRankDTO examRankDTO);
}
