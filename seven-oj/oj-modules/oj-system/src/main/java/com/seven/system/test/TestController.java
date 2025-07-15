package com.seven.system.test;

import com.seven.common.core.domain.R;
import com.seven.common.core.enums.ResultCode;
import com.seven.system.test.domain.LoginTestDTO;
import com.seven.system.test.service.ITestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private ITestService testService;

    @GetMapping("/list")
    public List<?> list() {
        return testService.list();
    }

    @GetMapping("/add")
    public String add() {
        return testService.add();
    }

    @GetMapping("/apifoxtest")
    public R<String> apiFoxTest(String apiId) {
        R<String> result = new R<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData("apxfoxtest:" + apiId);
        return result;
    }

    @PostMapping("/apifoxPost")
    public R<String> apiFoxPost(@RequestBody LoginTestDTO loginTestDTO) {
        R<String> result = new R<>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMsg());
        result.setData("apxfoxtest:" + loginTestDTO.getUserAccount() + ":" + loginTestDTO.getPassword());
        return result;
    }

    @GetMapping("/log")
    public String log() {
        log.info("我是 info");
        log.error("我是 error");
        return "日志测试";
    }
}
