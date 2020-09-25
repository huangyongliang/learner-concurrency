package com.hyl.learnerconcurrency.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * @author hyl
 * @version v1.0: TwinsLockTest.java, v 0.1 2020/9/26 1:34 $
 */
public class TwinsLockTest {

    public static void main(String[] args) {
        final Lock lock = new TwinsLock();
        final CountDownLatch start = new CountDownLatch(1);

        Runnable workRunnable = ()->{
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (true){
                lock.lock();
                try {
                    SleepUtils.second(1);
                    System.out.println(Thread.currentThread().getName());
                    SleepUtils.second(1);
                }finally {
                    lock.unlock();
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread worker = new Thread(workRunnable,"WorkThread-"+(i+1));
            worker.setDaemon(true);
            worker.start();
        }

        start.countDown();

        for (int i = 0; i < 100; i++) {
            SleepUtils.second(1);
            System.out.println();
        }

    }

}
