package com.luo.contorller;

import com.luo.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/config")
public class DemoController {

    @RequestMapping("/feign")
    public String test(@RequestBody User user){
        if("feign".equals(user.getUserName())){
            return "feign";
        }
        return "hello";
    }
    @RequestMapping("/sysmenu")
    public List<String> sysmenu() {
//        return menuService.getMenusByUserId();

        return  new ArrayList<String>(){{
            add("test");
        }};
    }

    @RequestMapping("/hello")
    public String test(){

        return "hello";
    }
}
