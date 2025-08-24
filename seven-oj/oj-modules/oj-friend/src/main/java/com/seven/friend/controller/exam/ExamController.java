package com.seven.friend.controller.exam;


import com.seven.common.core.Controller.BaseController;
import com.seven.common.core.domain.R;
import com.seven.common.core.domain.TableDataInfo;
import com.seven.friend.domain.exam.dto.ExamQueryDTO;
import com.seven.friend.domain.exam.dto.ExamRankDTO;
import com.seven.friend.service.exam.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exam")
public class ExamController extends BaseController {

    @Autowired
    private IExamService examService;

    @GetMapping("/semiLogin/list")
    public TableDataInfo list(ExamQueryDTO examQueryDTO) {
        return getTableDataInfo(examService.list(examQueryDTO));
    }

    @GetMapping("/semiLogin/redis/list")
    public TableDataInfo redisList(ExamQueryDTO examQueryDTO) {
        return examService.redisList(examQueryDTO);
    }

    @GetMapping("/rank/list")
    public TableDataInfo rankList(ExamRankDTO examRankDTO) {
        return examService.rankList(examRankDTO);
    }

    @GetMapping("/getFirstQuestion")
    public R<String> getFirstQuestion(Long examId) {
        return R.ok(examService.getFirstQuestion(examId));
    }

    @GetMapping("/preQuestion")
    public R<String> preQuestion(Long examId, Long questionId) {
        return R.ok(examService.preQuestion(examId, questionId));
    }

    @GetMapping("/nextQuestion")
    public R<String> nextQuestion(Long examId, Long questionId) {
        return R.ok(examService.nextQuestion(examId, questionId));
    }
}
