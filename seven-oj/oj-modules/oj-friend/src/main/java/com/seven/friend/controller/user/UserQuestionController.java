package com.seven.friend.controller.user;

import com.seven.common.core.Controller.BaseController;
import com.seven.common.core.domain.R;
import com.seven.friend.domain.user.dto.UserSubmitDTO;
import com.seven.friend.service.user.IUserQuestionService;
import com.seven.api.domain.vo.UserQuestionResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/question")
public class UserQuestionController extends BaseController {

    @Autowired
    private IUserQuestionService userQuestionService;

    @PostMapping("/submit")
    public R<UserQuestionResultVO> submit(@RequestBody UserSubmitDTO userSubmitDTO) {
        return userQuestionService.submit(userSubmitDTO);
    }

    @PostMapping("/rabbit/submit")
    public R<Void> rabbitSubmit(@RequestBody UserSubmitDTO userSubmitDTO) {
        return toR(userQuestionService.rabbitSubmit(userSubmitDTO));
    }

    @GetMapping("/exe/result")
    public R<UserQuestionResultVO> exeResult(Long examId, Long questionId, String currentTime) {
        return R.ok(userQuestionService.exeResult(examId, questionId, currentTime));
    }
}
