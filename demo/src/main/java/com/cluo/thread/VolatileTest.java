package com.cluo.thread;

import org.junit.Test;

public class VolatileTest {

    volatile boolean isStop = false;

    public void add() {
        while (!isStop) {
            System.out.println("start");
        }
        System.out.println("over");
    }

    public void over() {
        isStop = true;
    }

    public static void main(String[] args) throws InterruptedException {
        VolatileTest volatileTest = new VolatileTest();
        new Thread(() -> {
            volatileTest.add();
        }).start();
        Thread.sleep(1000);
        new Thread(() -> {
            volatileTest.over();
        }).start();
    }
    @Test
    public void test() throws Exception {

        Thread t1 = new Thread(() -> {
            this.over();
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            this.add();
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t2.start();
    }
}