package com.liubiao.concurrent.javathread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * join方法详解
 *
 * @author mc0710
 */
@Slf4j
public class JoinExercise {

    static int r = 0;
    static int r1 = 0;
    static int r2 = 0;

    public static void main(String[] args) throws InterruptedException {
        // 因为主线程和线程 t1 是并行执行的，t1 线程需要 1 秒之后才能算出 r=10，而主线程一开始就要打印 r 的结果，所以只能打印出0
        test1();
        test2();
        test3();
    }

    private static void test1() {
        Thread thread = new Thread(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r = 10;
        });
        thread.start();
        log.info("r的结果为：" + r);
    }

    private static void test2() throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r = 10;
        });
        thread.start();
        // 等待线程执行完，执行完后再输出自然是10了
        thread.join();
        // 有实效的join，设定最多等待时间
//        thread.join(1000);
        log.info("r的结果为：" + r);
    }

    /**
     * 下面代码 cost 大约多少秒？
     */
    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r1 = 10;
        });
        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            r2 = 20;
        });
        long start = System.currentTimeMillis();
        t1.start();
        t2.start();
        // 第一个 join：等待 t1 时, t2 并没有停止, 而在运行
        // 第二个 join：1s 后, 执行到此, t2 也运行了 1s, 因此也只需再等待 1s,所以将t1和t2的join颠倒，其结果也是一样的
        t1.join();
        t2.join();
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
    }
}

