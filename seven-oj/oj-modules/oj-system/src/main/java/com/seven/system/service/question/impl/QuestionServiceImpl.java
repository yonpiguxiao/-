package com.seven.system.service.question.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.system.domain.question.dto.QuestionQueryDTO;
import com.seven.system.domain.question.vo.QuestionVO;
import com.seven.system.mapper.question.QuestionMapper;
import com.seven.system.service.question.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements IQuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public List<QuestionVO> list(QuestionQueryDTO questionQueryDTO) {
        PageHelper.startPage(questionQueryDTO.getPageNum(), questionQueryDTO.getPageSize());
        return questionMapper.selectQuestionList(questionQueryDTO);
    }
}
