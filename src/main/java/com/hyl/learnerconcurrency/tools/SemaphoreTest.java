package com.hyl.learnerconcurrency.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * {@link java.util.concurrent.Semaphore }测试类
 * <p>
 *
 * @author hyl
 * @version v1.0: SemaphoreTest.java, v 0.1 2020/10/6 7:36 $
 */
public class SemaphoreTest {

    private static final int THREAD_COUNT = 30;

    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);

    private static Semaphore s = new Semaphore(10);

    public static void main(String[] args) {

        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(() -> {
                try {
                    s.acquire();
                    // 随机产生休眠时间
                    int time = new Object().hashCode() >> 28;
                    time = time == 0 ? 1 : time;
                    System.out.println(Thread.currentThread()
                        .getName() + ": save data >> " + time);
                    try {
                        Thread.sleep(time * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    s.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        threadPool.shutdown();

    }

}
