package com.liubiao.concurrent.monitor2;

import java.util.ArrayList;
import java.util.List;

/**
 * synchronized应用1：取款
 * <p>
 * 悲观互斥
 *
 * @author mc0710
 */
public class Application1 implements IAccount {

    private Integer balance;

    public Application1(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        synchronized (this) {
            return this.balance;
        }
    }

    @Override
    public void withdraw(Integer amount) {
        synchronized (this) {
            this.balance -= amount;
        }
    }

    public static void demo(IAccount account) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threads.add(
                    new Thread(
                            () -> {
                                account.withdraw(10);
                            }
                    )
            );
        }
        long start = System.nanoTime();
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance()
                + " cost: " + (end - start) / 1000_000 + " ms");

    }

    public static void main(String[] args) {
        IAccount iAccount = new Application1(10000);
        demo(iAccount);
        System.out.println(iAccount.getBalance());
    }
}
