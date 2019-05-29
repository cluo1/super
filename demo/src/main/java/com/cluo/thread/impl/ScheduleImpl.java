package com.cluo.thread.impl;

import com.cluo.thread.ScheduleService;
import org.junit.Test;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduleImpl implements ScheduleService {

    ScheduledExecutorService executorService =  Executors.newScheduledThreadPool(5);

    @Override
    public void startJob(int seconds) {
        System.out.println(Thread.currentThread().getName()+"-currentTime:"+new Date());
        executorService.scheduleAtFixedRate(()->{//已固定频率间隔执行任务
//        executorService.scheduleWithFixedDelay(()->{//已非固定频率间隔执行任务
            System.out.println(Thread.currentThread().getName()+"-startTime:"+new Date());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"-endTime:"+new Date());
            System.out.println("==========================================================");
        },3,seconds, TimeUnit.SECONDS);
    }

    @Override
    public void shutDown() {
        executorService.shutdown();
    }

    public static void main(String[] args) {
        ScheduleImpl schedule = new ScheduleImpl();
        schedule.startJob(3);
    }

}
