package com.hyl.learnerconcurrency.threads.example;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author hyl
 * @version v1.0: DefaultThreadPoolTest.java, v 0.1 2020/9/24 4:53 $
 */
public class DefaultThreadPoolTest {

    private static AtomicLong printNum = new AtomicLong();

    public static void main(String[] args) {

        int threadCount = 8;

        ThreadPool<TestRunner> threadPool = new DefaultThreadPool<>(threadCount);

        for (int i = 0; i < 10; i++) {
            threadPool.execute(new TestRunner());
        }

    }

    static class TestRunner implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() +": "+printNum.incrementAndGet() + " , "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }
    }
}
