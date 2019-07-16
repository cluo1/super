package com.luo.contorller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/feign")
    public String test(@RequestBody String str){
        if("feign".equals(str)){
            return "feign";
        }
        return "hello";
    }

    @RequestMapping("/hello")
    public String test(){

        return "hello";
    }
}
