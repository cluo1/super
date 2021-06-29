package com.luo.quartz;

import com.alibaba.fastjson.JSON;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author binge
 * @version 1.0
 * @description: TODO
 * @date 2021/5/14 14:11
 */
@RestController
@RequestMapping("testTask")
public class TestTaskController {

    @Autowired
    private QuartzScheduler quartzScheduler;

    @PostMapping("openTask")
    public String openTask(CronTask cronTask) {

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("id", UUID.randomUUID().toString());
        cronTask.setParameter("这是参数");
        cronTask.setParameterMap(paramMap);
        cronTask.setBeanClass("com.luo.quartz");
        cronTask.setJobName("TestTaskController");
        cronTask.setJobGroupName("");
        cronTask.setCronExpression("0 0 12 * * ? ");

        quartzScheduler.addJob(cronTask);
        return "添加新的任务成功";
    }


    @PostMapping("pause")
    public String pause(CronTask cronTask) throws SchedulerException {
        quartzScheduler.pauseJob(cronTask);
        return "添加新的任务成功";
    }

    @PostMapping("update")
    public String update(CronTask cronTask) throws SchedulerException {
        quartzScheduler.resumeJob(cronTask);
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("id", UUID.randomUUID().toString());
        quartzScheduler.updateJobCron(cronTask);
        return "添加新的任务成功";
    }

    @PostMapping("getAllTask")
    public String getAllTask(CronTask cronTask) throws SchedulerException {
        quartzScheduler.resumeJob(cronTask);
        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("id", UUID.randomUUID().toString());
        quartzScheduler.updateJobCron(cronTask);
        return "添加新的任务成功";
    }

}
