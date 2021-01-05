package com.liubiao.concurrent.monitor2;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * synchronized应用2：取款
 * <p>
 * 乐观重试: 利用cas
 *
 * @author mc0710
 */
public class Application2 implements IAccount {

    private AtomicInteger balance;

    public Application2(int balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        while (true) {
            // 获取余额的最新值
            int a = balance.get();
            // 要修改的值
            int b = a - amount;
            // 修改操作
            if (balance.compareAndSet(a, b)) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        IAccount account = new Application2(10000);
        System.out.println(account.getBalance());
        account.withdraw(10000);
        System.out.println(account.getBalance());

    }
}
