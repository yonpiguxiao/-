package com.seven.system.controller.question;


import com.seven.common.core.Controller.BaseController;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.system.domain.question.dto.QuestionQueryDTO;
import com.seven.system.domain.question.vo.QuestionVO;
import com.seven.system.service.question.IQuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/question")
@Tag(name = "题目管理接口")
public class QuestionController extends BaseController {

    @Autowired
    private IQuestionService questionService;

    @GetMapping("/list")
    public TableDataInfo list(QuestionQueryDTO questionQueryDTO) {
        return getTableDataInfo(questionService.list(questionQueryDTO));
    }

}
