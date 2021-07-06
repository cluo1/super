package com.luo.contorller;

import com.alibaba.fastjson.JSONObject;
import com.luo.entity.User;
import com.luo.service.ISqlLoaderService;
import com.luo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    private Logger log = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private ISqlLoaderService iSqlLoaderService;

    @Autowired
    private UserService userService ;
    @Value("${memory.test.count}")
    private int memoryCount;

    @RequestMapping("/hello")
    public String test() {

        List<Object> list = new ArrayList<>();
        while (memoryCount>0){
            User user = new User(1,memoryCount+"");
            log.info(JSONObject.toJSONString(user));
            list.add(user);
            memoryCount--;
        }

        return "hello";
    }

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



    /**
     * @param userName
     * @param password
     * @param database
     * @param filePath
     * @param ctlFileName
     * @param dataFileName
     * @param tableName
     * @param filedName
     * @param split
     *         例：
     *         userName=iwssdev
     *         password=iwssdev
     *         database=192.168.90.237/orcl.zhang
     *         filePath=H:/document/zy
     *         ctlFileName=WMS_T1_CUST_INFO
     *         dataFileName=WMS_T1_CUST_INFO.txt
     *         tableName=test1
     *         filedName=(TEST_DATE FILLER,CUST_NO, JI_FEN, DETAIL_TYPE "to_number(:DETAIL_TYPE)")
     *         split=~@~
     */
    @RequestMapping("/sqlloder")
    public void sqlloder(String userName, String password,
                         String database, String filePath, String ctlFileName, String dataFileName, String tableName, String filedName, String split) {
//        TestSqlLoader testSqlLoader = new TestSqlLoader();
//
//        testSqlLoader.ctlFileWriter(strctl,filePath,ctlFileName);
//        testSqlLoader.sqlloder(userName, password,database, filePath, ctlFileName,filePath+"/"+dataFileName);
        String skip = "0";
        String ctlTemplet = iSqlLoaderService.getCtlTemplet(filePath, dataFileName,skip, tableName, split, filedName);
        iSqlLoaderService.ctlFileWriter(ctlTemplet, filePath, ctlFileName);
        iSqlLoaderService.sqlloder(userName, password, database, filePath, ctlFileName, filePath + "/" + dataFileName);

    }
}
