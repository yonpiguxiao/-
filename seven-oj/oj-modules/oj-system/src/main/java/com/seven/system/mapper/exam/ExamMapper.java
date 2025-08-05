package com.seven.system.mapper.exam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seven.system.domain.exam.Exam;
import com.seven.system.domain.exam.dto.ExamQueryDTO;
import com.seven.system.domain.exam.vo.ExamVO;
import java.util.List;

public interface ExamMapper extends BaseMapper<Exam> {
    List<ExamVO> selectExamList(ExamQueryDTO examQueryDTO);
}
