package com.cluo.thread;

import org.junit.Test;

import java.util.concurrent.*;

public class countDownLatch {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(100);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for(int i=0;i<100;i++){
            final int n = i;
            executorService.execute(()->{
                try {
                    Thread.sleep(100);
                    System.out.println(n);
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await(100, TimeUnit.MILLISECONDS);
        System.out.println("over");
        executorService.shutdown();
    }
}
