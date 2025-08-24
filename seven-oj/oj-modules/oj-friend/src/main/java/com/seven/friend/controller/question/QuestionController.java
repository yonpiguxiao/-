package com.seven.friend.controller.question;

import com.seven.common.core.Controller.BaseController;
import com.seven.common.core.domain.R;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.friend.domain.question.dto.QuestionQueryDTO;
import com.seven.friend.domain.question.es.QuestionDetailVO;
import com.seven.friend.domain.question.vo.QuestionVO;
import com.seven.friend.service.question.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController extends BaseController {

    @Autowired
    private IQuestionService questionService;


    @GetMapping("/semiLogin/list")
    public TableDataInfo list(QuestionQueryDTO questionQueryDTO) {
        return questionService.list(questionQueryDTO);
    }

    @GetMapping("/semiLogin/hotList")
    public R<List<QuestionVO>> hotList() {
        return R.ok(questionService.hotList());
    }

    @GetMapping("/detail")
    public R<QuestionDetailVO> detail(Long questionId) {
        return R.ok(questionService.detail(questionId));
    }


    @GetMapping("/preQuestion")
    public R<String> preQuestion(Long questionId) {
        return R.ok(questionService.preQuestion(questionId));
    }

    @GetMapping("/nextQuestion")
    public R<String> nextQuestion(Long questionId) {
        return R.ok(questionService.nextQuestion(questionId));
    }
}
