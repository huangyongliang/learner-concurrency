package com.hyl.learnerconcurrency.tools;

import java.util.concurrent.CountDownLatch;

/**
 * {@link CountDownLatch} 测试类
 * <p>
 *
 * @author hyl
 * @version v1.0: CountDownLatchTest.java, v 0.1 2020/10/6 8:33 $
 */
public class CountDownLatchTest {

    static final int THREAD_COUNT = 4;

    static CountDownLatch start = new CountDownLatch(1);

    static CountDownLatch end = new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () -> {
            System.out.println(Thread.currentThread()
                .getName() + ": start");
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread()
                .getName() + ": end");
            end.countDown();
        };

        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(runnable, "run-thread-" + i);
            thread.start();
        }

        start.countDown();
        end.await();
        System.out.println(Thread.currentThread()
            .getName() + ": final");
    }

}



