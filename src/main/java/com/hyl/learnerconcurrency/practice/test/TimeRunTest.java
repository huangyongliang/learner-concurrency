package com.hyl.learnerconcurrency.practice.test;

import java.util.concurrent.TimeUnit;

import com.hyl.learnerconcurrency.practice.TimeRun;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: TimeRun.java, v 0.1 2020/10/14 12:07 $
 */
public class TimeRunTest {

    public static void main(String[] args) {

        Runnable r = ()->{
            Object lock = new Object();
            int count =0;
            synchronized (lock){
                while (!Thread.currentThread().isInterrupted() && count< 100){
                    try {
                        lock.wait(10);
                        System.out.println(count++);
                    } catch (InterruptedException e) {
                       // throw new RuntimeException(e.getCause());
                         Thread.currentThread().interrupt();
                    }

                }
            }
        };

        try {
            TimeRun.timedRun(r,50, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TimeRun.shutDown();

    }



}
