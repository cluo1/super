package com.luo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SaveMoneyTest {
    public static void main(String[] args) {
        Account account = new Account();
        ExecutorService service = Executors.newFixedThreadPool(100);
        long time = System.currentTimeMillis();
        //持续一分钟
        while (System.currentTimeMillis()-time<1000*60){
            service.execute(new MoneyThread(account, 0.01));
        }
        service.shutdown();
        System.out.println("账户金额："+account.getBalance());
        //更新表余额
//        update(account);
    }

}
