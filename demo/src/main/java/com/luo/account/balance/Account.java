package com.luo.account.balance;

public class Account {
    private String acuntId;
    private double balance=0;			//账户余额

    public synchronized void add(double money) {
        double newBalance = getBalance() + money;

        this.setBalance(newBalance);

    }
    /**
     * 获得账户金额
     */
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
