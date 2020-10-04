package com.hyl.learnerconcurrency.collection;

import java.util.concurrent.SynchronousQueue;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * @author hyl
 * @version v1.0: SynchronousQueueTest.java, v 0.1 2020/10/4 14:59 $
 */
public class SynchronousQueueTest {

    public static void main(String[] args) throws InterruptedException {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();

        Thread putThread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("put thread 1 start");
                    try {
                        queue.put(1);
                        SleepUtils.second(1);
                    } catch (InterruptedException e) {
                    }
                    System.out.println("put thread 1 end");
                }
                }

        });
        Thread putThread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("put thread 2 start");
                    try {
                        queue.put(2);
                        SleepUtils.second(1);
                    } catch (InterruptedException e) {
                    }
                    System.out.println("put thread 2 end");
                }
            }
        });

        Thread takeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println("take thread start");
                    try {
                        System.out.println("take from putThread: " + queue.take());
                        SleepUtils.second(1);
                    } catch (InterruptedException e) {
                    }
                    System.out.println("take thread end");
                }
            }
        });

        putThread1.start();
        putThread2.start();
        Thread.sleep(1000);
        takeThread.start();
    }

}
