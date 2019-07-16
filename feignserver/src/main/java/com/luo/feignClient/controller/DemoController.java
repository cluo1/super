package com.luo.feignClient.controller;

import com.luo.feignClient.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/feign")
public class DemoController {

    @Autowired
    DemoService demoService;
    @RequestMapping("/hello")
    public String hello() throws Exception {
        String feign = demoService.hello("feign");
        return feign;
    }


}
