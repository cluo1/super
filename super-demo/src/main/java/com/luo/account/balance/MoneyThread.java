package com.luo.account.balance;

public class MoneyThread implements Runnable{
    private Account account;		//存入账户

    private double money;			//存入金额


    public MoneyThread(Account account, double money) {
        this.account = account;
        this.money = money;
    }

    @Override
    public void run() {
        account.add(money);
    }
}
