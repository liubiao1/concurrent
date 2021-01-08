package com.liubiao.concurrent.monitor2;

import lombok.extern.slf4j.Slf4j;

/**
 * @author mc0710
 */
@Slf4j
public class Test31 {

    /**
     * 修饰成员方法，锁的是this对象
     *
     * @throws InterruptedException 线程打断异常
     */
    public synchronized void test1() throws InterruptedException {
        Thread.sleep(1000);
        log.info("1");
    }

    public synchronized void test2() {
        log.info("2");
    }

    public static void main(String[] args) {
        Test31 test31 = new Test31();
        Thread t1 = new Thread(() -> {
            try {
                test31.test1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "哈哈");
        Thread t2 = new Thread(() -> test31.test2(), "嘿嘿");
        t1.start();
        t2.start();
    }

}
