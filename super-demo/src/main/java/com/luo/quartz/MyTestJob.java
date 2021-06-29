package com.luo.quartz;

import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @author binge
 * @version 1.0
 * @description: TODO
 * @date 2021/6/11 18:24
 */
public class MyTestJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date =new Date();
        System.err.println("111111" + DateFormatUtils.format(date,"yyyyMMdd HH:mm:ss"));
    }
}
