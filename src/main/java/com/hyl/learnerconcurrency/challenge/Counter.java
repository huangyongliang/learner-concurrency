package com.hyl.learnerconcurrency.challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计数器
 *
 * @author hyl
 * @version v1.0: Counter.java, v 0.1 2020/9/20 17:34 $
 */
public class Counter {

    private AtomicInteger atomicI = new AtomicInteger(0);

    private int i = 0;

    /**
     * 使用 CAS 实现线程安全计数器
     */
    private void safeCount() {
        for (; ; ) {
            int i = atomicI.get();
            boolean suc = atomicI.compareAndSet(i, ++i);
            if (suc) {
                break;
            }
        }
    }

    /**
     * 非线程安全计数器
     */
    private void count() {
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
        final Counter counter = new Counter();
        List<Thread> threads = new ArrayList<>(600);

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    counter.safeCount();
                    counter.count();
                }
            });
            threads.add(thread);
        }

        threads.forEach(Thread::start);

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println(counter.i);
        System.out.println(counter.atomicI.get());
        System.out.println(System.currentTimeMillis()-start);

    }

}
