package com.hyl.learnerconcurrency.challenge;

/**
 * @author hyl
 * @version v1.0: ShareVariables.java, v 0.1 2020/9/20 18:29 $
 */
public class ShareVariables {

    static int a = 0, b = 0;
    static int x, y;

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            a = 1;
            x = b;
        });
        Thread thread2 = new Thread(() -> {
            b = 2;
            y = a;
        });

        Thread.sleep(200);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("a = " + a + ", b = " + b);
        System.out.println("x = " + x + ", y = " + y);
    }

}
