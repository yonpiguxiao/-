package com.seven.friend.controller.user;

import com.seven.common.core.Controller.BaseController;
import com.seven.common.core.constants.HttpConstants;
import com.seven.common.core.domain.R;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.friend.aspect.CheckUserStatus;
import com.seven.friend.domain.exam.dto.ExamDTO;
import com.seven.friend.domain.exam.dto.ExamQueryDTO;
import com.seven.friend.service.user.IUserExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/exam")
public class UserExamController extends BaseController {
    @Autowired
    private IUserExamService userExamService;

    @CheckUserStatus
    @PostMapping("/enter")
    public R<Void> enter(@RequestHeader(HttpConstants.AUTHENTICATION) String token, @RequestBody ExamDTO examDTO) {
        return toR(userExamService.enter(token, examDTO.getExamId()));
    }

    @GetMapping("/list")
    public TableDataInfo list(ExamQueryDTO examQueryDTO) {
        return userExamService.list(examQueryDTO);
    }
}
