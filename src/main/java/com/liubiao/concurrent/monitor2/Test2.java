package com.liubiao.concurrent.monitor2;

/**
 * synchronized (room) , 对象锁解决Test1带来的问题
 *
 * @author mc0710
 */
public class Test2 {

    private static int a = 0;
    static final Object room = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                synchronized (room) {
                    System.out.println("haha");
                    a++;
                }
            }
        }, "哈哈"
        );


        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                synchronized (room) {
                    System.out.println("heihei");
                    a--;
                }
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
