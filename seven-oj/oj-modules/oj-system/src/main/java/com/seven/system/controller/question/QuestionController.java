package com.seven.system.controller.question;


import com.seven.common.core.Controller.BaseController;
import com.seven.common.core.domain.R;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.system.domain.question.dto.QuestionAddDTO;
import com.seven.system.domain.question.dto.QuestionEditDTO;
import com.seven.system.domain.question.dto.QuestionQueryDTO;
import com.seven.system.domain.question.vo.QuestionDetailVO;
import com.seven.system.domain.question.vo.QuestionVO;
import com.seven.system.service.question.IQuestionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public R<Void> add(@RequestBody QuestionAddDTO questionAddDTO) {
        return toR(questionService.add(questionAddDTO));
    }

    @GetMapping("/detail")
    public R<QuestionDetailVO> detail(Long questionId) {
        return R.ok(questionService.detail(questionId));
    }

    @PutMapping("/edit")
    public R<Void> edit(@RequestBody QuestionEditDTO questionEditDTO) {
        return toR(questionService.edit(questionEditDTO));
    }

    @DeleteMapping("/delete")
    public R<Void> delete(Long questionId) {
        return toR(questionService.delete(questionId));
    }
}
