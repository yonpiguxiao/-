package com.seven.system.controller.exam;


import com.seven.common.core.Controller.BaseController;
import com.seven.common.core.domain.R;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.common.core.enums.ResultCode;
import com.seven.common.security.exception.ServiceException;
import com.seven.system.domain.exam.dto.ExamAddDTO;
import com.seven.system.domain.exam.dto.ExamQueryDTO;
import com.seven.system.domain.exam.dto.ExamQuestionAddDTO;
import com.seven.system.service.exam.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/exam")
public class ExamController extends BaseController {

    @Autowired
    private IExamService examService;

    @GetMapping("/list")
    public TableDataInfo list(ExamQueryDTO examQueryDTO) {
        return getTableDataInfo(examService.list(examQueryDTO));
    }

    @PostMapping("/add")
    public R<Void> add(@RequestBody ExamAddDTO examAddDTO) {
        return toR(examService.add(examAddDTO));
    }

    @PostMapping("/question/add")
    public R<Void> questionAdd(@RequestBody ExamQuestionAddDTO examQuestionAddDTO) {
        return toR(examService.questionAdd(examQuestionAddDTO));
    }
}
