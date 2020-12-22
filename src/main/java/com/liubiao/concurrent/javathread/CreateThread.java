package com.liubiao.concurrent.javathread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.FutureTask;

/**
 * @author mc0710
 */
@Slf4j
public class CreateThread {

    public static void main(String[] args) {

        log.info("继承Thread创建");

        Thread thread = new Thread("1") {
            @Override
            public void run() {
                log.info("run");
            }
        };
        thread.start();

        log.info("实现runnable");

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info("run");
            }
        };
        Thread thread2 = new Thread(runnable, "2");
        thread2.start();

        log.info("java8,实现runnable");
        Runnable r = () -> log.info("run");
        Thread thread3 = new Thread(r, "3");
        thread3.start();

        log.info("FutureTask和Callable实现");
        FutureTask<Integer> task = new FutureTask<>(() -> {
            log.info("run");
            // 因为是Callable是有返回值的
            return 100;
        });
        Thread thread4 = new Thread(task, "4");
        thread4.start();
    }
}
