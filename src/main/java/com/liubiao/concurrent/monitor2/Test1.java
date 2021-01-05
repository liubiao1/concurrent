package com.liubiao.concurrent.monitor2;

/**
 * 共享问题：2个线程争夺同一资源
 * <p>
 * 结果不一定未0
 *
 * @author mc0710
 */
public class Test1 {

    private static int a = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                a++;
            }
        }, "哈哈"
        );


        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                a--;
            }
        }, "呵呵"
        );

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(a);
    }

}
