package com.hyl.learnerconcurrency.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * 独占式锁测试类
 *
 * @author hyl
 * @version v1.0: MutexTest.java, v 0.1 2020/9/25 13:37 $
 */
public class MutexTest {
    // 保留所有 Runner 能够同时开始
    static CountDownLatch start = new CountDownLatch(1);
    static CountDownLatch end ;
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new Mutex();
        final  int threadCount  = 20;
        end = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new TestRunner(lock),"TestThread-"+i);
            thread.start();
        }
        long startLong = System.currentTimeMillis();
        start.countDown();
        end.await();

        System.out.println("执行完毕，用时："+(System.currentTimeMillis()- startLong));
    }

    static class TestRunner implements Runnable{

        private Lock lock;
        private long count = 0;

        public TestRunner(Lock lock){
            this.lock = lock;
        }

        @Override
        public void run() {

            try {
                start.await();
            } catch (Exception ex) {
            }

            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+": 获得到了锁，sleep 1 s。竞争次数："+count);
                SleepUtils.second(1);
            }finally {
                lock.unlock();
            }

            end.countDown();
        }
    }
}
