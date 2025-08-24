package com.seven.friend.test;

import com.seven.common.core.Controller.BaseController;
import com.seven.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test/nginx")
@Slf4j
public class NginxTestController  extends BaseController {

    @GetMapping("/info")
    public R<Void> info() {
        log.info("负载均衡测试");
        return R.ok();
    }

//    location /sevenoj-dev/ {
//        proxy_pass http://sevenoj/;
//    }

//    upstream sevenoj {
//        server 192.168.31.236:9202 weight=1;
//        server 192.168.31.236:19202 weight=2;
//    }

}