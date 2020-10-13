package com.hyl.learnerconcurrency.api;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: LongTest.java, v 0.1 2020/10/10 17:32 $
 */
public class LongTest {
    static User user = new User();

    static CountDownLatch countDownLatch = new CountDownLatch(8);

    static AtomicLongFieldUpdater<User> a = AtomicLongFieldUpdater.newUpdater(User.class,"count");

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(8);

        Runnable runnable = ()->{
            for (int i = 0; i < 100000; i++) {
                    a.getAndIncrement(user);
            }
            countDownLatch.countDown();
        };

        for (int i = 0; i < 8; i++) {
            executor.execute(runnable);
        }
        countDownLatch.await();
        System.out.println(user.count);

        executor.shutdown();
    }


    static class User{
        volatile  long count;
    }

}
