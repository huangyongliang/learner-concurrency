package com.hyl.learnerconcurrency.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 锁降级
 *
 * @author hyl
 * @version v1.0: ProcessDataTest.java, v 0.1 2020/9/28 1:45 $
 */
public class ProcessDataTest {
    static volatile  boolean update =true;
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();

    public static void main(String[] args) throws InterruptedException {
        final  int countThread = 5;
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch end = new CountDownLatch(countThread);
        Runnable runnable = ()->{
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            processData();
            end.countDown();
        };

        for (int i = 0; i < countThread; i++) {
            Thread thread = new Thread(runnable,"run-thread-"+i);
            thread.start();
        }
        start.countDown();
        update = false;
        end.await();
    }

    private static void processData(){
        r.lock();
        if (!update){
            // 必须先释放读锁
            r.unlock();
            // 锁降级从写锁获取到开始
            w.lock();
            try {
                if (!update){
                    System.out.println(Thread.currentThread().getName()+":更新数据");
                    update = true;
                }
                r.lock();
            }finally {
                w.unlock();
            }
            // 降级锁完成，写锁降级为读锁
        }
        try {
            System.out.println(Thread.currentThread().getName()+":读取数据");
        }finally {
            r.unlock();
        }

    }


}
