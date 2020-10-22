package com.hyl.learnerconcurrency.practice.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.hyl.learnerconcurrency.practice.OneShotLatch;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: OneShotLatchTest.java, v 0.1 2020/10/21 19:41 $
 */
public class OneShotLatchTest {
    public static void main(String[] args) throws InterruptedException {

        OneShotLatch latch = new OneShotLatch();

        ExecutorService exec = Executors.newFixedThreadPool(11);
        Runnable runnable = ()->{
            System.out.println(Thread.currentThread().getName()+":开始进入阻塞!");
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+":结束阻塞!");
        };

        Runnable runnable1 = ()->{
            System.out.println(Thread.currentThread().getName()+":开始解除其他线程阻塞!");
            latch.signal();
        };

        for (int i = 0; i < 10; i++) {
            exec.execute(runnable);
        }
        TimeUnit.SECONDS.sleep(2);

        exec.execute(runnable1);
    }
}
