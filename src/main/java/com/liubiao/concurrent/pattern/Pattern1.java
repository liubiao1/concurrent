package com.liubiao.concurrent.pattern;

import lombok.extern.slf4j.Slf4j;

/**
 * wait notify应用： 保护性暂停模式（即 Guarded Suspension，用在一个线程等待另一个线程的执行结果）
 *
 * <p>
 * 要点：
 * 1、有一个结果需要从一个线程传递到另一个线程，让他们关联同一个 GuardedObject
 * 2、如果有结果不断从一个线程到另一个线程那么可以使用消息队列（见生产者/消费者）
 * 3、JDK 中，join 的实现、Future 的实现，采用的就是此模式
 * 4、因为要等待另一方的结果，因此归类到同步模式
 * <p>
 * join 的实现、Future 的实现、同步模式
 *
 *
 * @author mc0710
 */
@Slf4j
public class Pattern1 {

    private Object resp;
    private final Object lock = new Object();

    public Object get() throws InterruptedException {
        synchronized (lock) {
            while (resp == null) {
                lock.wait();
                log.info("哈哈，我终于能执行了");
            }
        }
        return resp;
    }


    /**
     * @param resp get()方法的结果
     */
    public void complete(Object resp) {
        synchronized (lock) {
            this.resp = resp;
            lock.notify();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Pattern1 pattern1 = new Pattern1();

        new Thread(() -> {
            try {
                log.info("进入子线程");
                Thread.sleep(2000);
                log.info("子线程: complete执行完成，准备唤醒主线程");
                pattern1.complete(new Object());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();
        log.info("主线程：阻塞等待~~~");
        Object o = pattern1.get();
    }
}
