package com.hyl.learnerconcurrency.practice.test;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: InterruptTest.java, v 0.1 2020/10/13 23:15 $
 */
public class InterruptTest {

    public static void main(String[] args) {

        Runnable runnable = ()->{
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    System.out.println(Thread.currentThread().getName()+":被打断了");
                    return;
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        SleepUtils.second(2);
        thread.interrupt();



    }



}
