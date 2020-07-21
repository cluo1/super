package com.luo.contorller;

import com.luo.entity.User;
import com.luo.utils.TestSqlLoader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/demo")
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

    @RequestMapping("/sqlloder")
    public void sqlloder(String username, String password,
                           String database, String ctlFileDir, String ctlFileName,String dataFileName){

        TestSqlLoader testSqlLoader = new TestSqlLoader();
        testSqlLoader.sqlloder(username, password,database, ctlFileDir, ctlFileName,dataFileName);

    }
}
