package com.seven.system.test;

import com.seven.system.test.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ITestService testService;

    @GetMapping("/list")
    public List<?> list() {
        return testService.list();
    }

}
