package com.cluo.thread;

import org.junit.Test;

import java.util.concurrent.*;

public class CallableTest implements Callable<String>
{
    @Override
    public String call() throws Exception {
        return "str";
    }

    public void test() {
        FutureTask futureTask = new FutureTask(new CallableTest());
        new Thread(futureTask).start();
        try {
            String str = (String)futureTask.get();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1(){

/*ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"运行");
            }
        });
        executorService.shutdown();*/

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        scheduledExecutorService.schedule(()->{
            System.out.println("5");
        },5,TimeUnit.SECONDS);
    }
}
