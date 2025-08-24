package com.seven.system.service.question.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.seven.common.core.constants.Constants;
import com.seven.common.core.enums.ResultCode;
import com.seven.common.security.exception.ServiceException;
import com.seven.system.domain.question.Question;
import com.seven.system.domain.question.dto.QuestionAddDTO;
import com.seven.system.domain.question.dto.QuestionEditDTO;
import com.seven.system.domain.question.dto.QuestionQueryDTO;
import com.seven.system.domain.question.es.QuestionES;
import com.seven.system.domain.question.vo.QuestionDetailVO;
import com.seven.system.domain.question.vo.QuestionVO;
import com.seven.system.elasticsearch.QuestionRepository;
import com.seven.system.mapper.QuestionCacheManager;
import com.seven.system.mapper.question.QuestionMapper;
import com.seven.system.service.question.IQuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements IQuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionCacheManager questionCacheManager;

    @Override
    public List<QuestionVO> list(QuestionQueryDTO questionQueryDTO) {
        String excludeIdStr = questionQueryDTO.getExcludeIdStr();
        if(StrUtil.isNotEmpty(excludeIdStr)) {
            String[] excludeIdArr = excludeIdStr.split(Constants.SPLIT_SEN);
            Set<Long> excludeIdSet = Arrays.stream(excludeIdArr)
                    .map(Long::valueOf)
                    .collect(Collectors.toSet());
            questionQueryDTO.setExcludeIdSet(excludeIdSet);
        }
        PageHelper.startPage(questionQueryDTO.getPageNum(), questionQueryDTO.getPageSize());
        return questionMapper.selectQuestionList(questionQueryDTO);
    }

    @Override
    public boolean add(QuestionAddDTO questionAddDTO) {
        List<Question> questionList = questionMapper.selectList(new LambdaQueryWrapper<Question>().eq(Question::getTitle, questionAddDTO.getTitle()));
        if(CollectionUtil.isNotEmpty(questionList)) {
            throw new ServiceException(ResultCode.FAILED_ALREADY_EXISTS);
        }
        Question question = new Question();
        BeanUtils.copyProperties(questionAddDTO, question);
        int insert = questionMapper.insert(question);
        if (insert <= 0) {
            return false;
        }
        QuestionES questionES = new QuestionES();
        BeanUtil.copyProperties(question, questionES);
        questionRepository.save(questionES);
        questionCacheManager.addCache(question.getQuestionId());
        return true;
    }

    @Override
    public QuestionDetailVO detail(Long questionId) {
        Question question = questionMapper.selectById(questionId);
        if(question == null) {
            throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);
        }
        QuestionDetailVO questionDetailVO = new QuestionDetailVO();
        BeanUtils.copyProperties(question, questionDetailVO);
        return questionDetailVO;
    }

    @Override
    public int edit(QuestionEditDTO questionEditDTO) {
        Question oldQuestion = questionMapper.selectById(questionEditDTO.getQuestionId());
        if(oldQuestion == null) {
            throw new ServiceException(ResultCode.FAILED_ALREADY_EXISTS);
        }
        oldQuestion.setQuestionCase(questionEditDTO.getQuestionCase());
        oldQuestion.setContent(questionEditDTO.getContent());
        oldQuestion.setDifficulty(questionEditDTO.getDifficulty());
        oldQuestion.setDefaultCode(questionEditDTO.getDefaultCode());
        oldQuestion.setMainFunc(questionEditDTO.getMainFunc());
        oldQuestion.setSpaceLimit(questionEditDTO.getSpaceLimit());
        oldQuestion.setTimeLimit(questionEditDTO.getTimeLimit());
        oldQuestion.setTitle(questionEditDTO.getTitle());
        QuestionES questionES = new QuestionES();
        BeanUtil.copyProperties(oldQuestion, questionES);
        questionRepository.save(questionES);
        return questionMapper.updateById(oldQuestion);
    }

    @Override
    public int delete(Long questionId) {
        Question question = questionMapper.selectById(questionId);
        if(question == null) {
            throw new ServiceException(ResultCode.FAILED_NOT_EXISTS);
        }
        questionRepository.deleteById(questionId);
        questionCacheManager.deleteCache(questionId);
        return questionMapper.deleteById(questionId);
    }
}
