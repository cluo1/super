package com.luo.contorller;

import com.luo.entity.User;
import com.luo.service.ISqlLoaderService;
import com.luo.utils.TestSqlLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private ISqlLoaderService iSqlLoaderService;

    @RequestMapping("/feign")
    public String test(@RequestBody User user) {
        if ("feign".equals(user.getUserName())) {
            return "feign";
        }
        return "hello";
    }

    @RequestMapping("/sysmenu")
    public List<String> sysmenu() {
//        return menuService.getMenusByUserId();

        return new ArrayList<String>() {{
            add("test");
        }};
    }

    @RequestMapping("/hello")
    public String test() {

        return "hello";
    }

    @RequestMapping("/sqlloder")
    public void sqlloder(String userName, String password,
                         String database, String filePath, String ctlFileName, String dataFileName, String tableName, String filedName, String split) {
//        TestSqlLoader testSqlLoader = new TestSqlLoader();
//
//        testSqlLoader.ctlFileWriter(strctl,filePath,ctlFileName);
//        testSqlLoader.sqlloder(userName, password,database, filePath, ctlFileName,filePath+"/"+dataFileName);
        String ctlTemplet = iSqlLoaderService.getCtlTemplet(filePath, dataFileName, tableName, split, filedName);
        iSqlLoaderService.ctlFileWriter(ctlTemplet, filePath, ctlFileName);
        iSqlLoaderService.sqlloder(userName, password, database, filePath, ctlFileName, filePath + "/" + dataFileName);

    }
}
