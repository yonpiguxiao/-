package com.seven.system.service.exam.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.seven.common.core.enums.ResultCode;
import com.seven.common.security.exception.ServiceException;
import com.seven.system.domain.exam.Exam;
import com.seven.system.domain.exam.ExamQuestion;
import com.seven.system.domain.exam.dto.ExamAddDTO;
import com.seven.system.domain.exam.dto.ExamQueryDTO;
import com.seven.system.domain.exam.dto.ExamQuestionAddDTO;
import com.seven.system.domain.exam.vo.ExamVO;
import com.seven.system.domain.question.Question;
import com.seven.system.mapper.exam.ExamMapper;
import com.seven.system.mapper.exam.ExamQuestionMapper;
import com.seven.system.mapper.question.QuestionMapper;
import com.seven.system.service.exam.IExamService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ExamServiceImpl extends ServiceImpl<ExamQuestionMapper, ExamQuestion> implements IExamService {

    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ExamQuestionMapper examQuestionMapper;

    @Override
    public List<ExamVO> list(ExamQueryDTO examQueryDTO) {
        PageHelper.startPage(examQueryDTO.getPageNum(), examQueryDTO.getPageSize());
        return examMapper.selectExamList(examQueryDTO);
    }

    @Override
    public int add(ExamAddDTO examAddDTO) {
        List<Exam> examList = examMapper.selectList(new LambdaQueryWrapper<Exam>().eq(Exam::getTitle, examAddDTO.getTitle()));
        if(CollectionUtil.isNotEmpty(examList)) {
            throw new ServiceException(ResultCode.FAILED_ALREADY_EXISTS);
        }
        if(examAddDTO.getStartTime().isBefore(LocalDateTime.now())) {
            throw new ServiceException(ResultCode.EXAM_START_TIME_BEFORE_CURRENT_TIME);
        }
        if(examAddDTO.getStartTime().isAfter(examAddDTO.getEndTime())) {
            throw new ServiceException(ResultCode.EXAM_END_TIME_BEFORE_START_TIME);
        }
        Exam exam = new Exam();
        BeanUtils.copyProperties(examAddDTO, exam);
        return examMapper.insert(exam);
    }

    @Override
    public boolean questionAdd(ExamQuestionAddDTO examQuestionAddDTO) {
        Exam exam = getExam(examQuestionAddDTO);
        Set<Long> questionIdSet = examQuestionAddDTO.getQuestionIdSet();
        if(CollectionUtil.isEmpty(questionIdSet)) {
            return true;
        }
        List<Question> questionList = questionMapper.selectBatchIds(questionIdSet);
        if(CollectionUtil.isEmpty(questionList) || questionList.size() < questionIdSet.size()) {
            throw new ServiceException(ResultCode.EXAM_QUESTION_NOT_EXISTS);
        }
        return saveExamQuestion(exam, questionIdSet);
    }

    private boolean saveExamQuestion(Exam exam, Set<Long> questionIdSet) {
        int num = 1;
        List<ExamQuestion> examQuestionList = new ArrayList<>();
        for(Long questionId : questionIdSet) {
            ExamQuestion examQuestion = new ExamQuestion();
            examQuestion.setQuestionId(questionId);
            examQuestion.setExamId(exam.getExamId());
            examQuestion.setQuestionOrder(num++);
            examQuestionList.add(examQuestion);
        }
        return saveBatch(examQuestionList);
    }

    private Exam getExam(ExamQuestionAddDTO examQuestionAddDTO) {
        Exam exam = examMapper.selectById(examQuestionAddDTO.getExamId());
        if(exam == null) {
            throw new ServiceException(ResultCode.EXAM_NOT_EXISTS);
        }
        return exam;
    }
}
