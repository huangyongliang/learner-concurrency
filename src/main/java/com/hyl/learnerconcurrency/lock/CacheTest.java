package com.hyl.learnerconcurrency.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * @author hyl
 * @version v1.0: CacheTest.java, v 0.1 2020/9/27 1:58 $
 */
public class CacheTest {

    static final int count = 5;

    static CountDownLatch start = new CountDownLatch(1);
    static CountDownLatch end = new CountDownLatch(count);
    static CountDownLatch finalEnd = new CountDownLatch(2);
    static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < count; i++) {
            Thread thread = new Thread(new WriteRunner(),"Write-"+(i+1));
            thread.start();
        }
        Thread readThread = new Thread(new ReadRunner(),"ReadThread-1");
        readThread.start();
        Thread readThread2 = new Thread(new ReadRunner2(),"ReadThread-2");
        readThread2.start();
        start.countDown();
        end.await();




        flag = false;
        finalEnd.await();
        System.out.println("最后值：");
        System.out.println(Cache.toStr());

    }


    static class WriteRunner implements Runnable{

        @Override
        public void run() {

            try {
                start.await();
            }catch (InterruptedException e){

            }

            for (int i = 1; i <= 10; i++) {
                Cache.put(Thread.currentThread().getName()+"-"+i,i);
                SleepUtils.second(1);
            }
            end.countDown();
        }
    }

    static class ReadRunner implements Runnable{

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public void run() {

            try {
                start.await();
            }catch (InterruptedException e){

            }
            while (flag){
                SleepUtils.second(2);
                System.out.println(count.incrementAndGet()+":"+Cache.toStr());
            }

            finalEnd.countDown();
        }
    }

    static class ReadRunner2 implements Runnable{

        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public void run() {

            try {
                start.await();
            }catch (InterruptedException e){

            }
            while (flag){
                SleepUtils.second(2);
                System.out.println(count.incrementAndGet()+":"+Cache.toStr2());
            }

            finalEnd.countDown();
        }
    }


}
