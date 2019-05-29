package com.luo.contorller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RequestMapping("/testSpringboot")
    public String test(){
        System.out.println("OK");
        return "OK";
    }
}
