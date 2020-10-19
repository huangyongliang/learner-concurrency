package com.hyl.learnerconcurrency.practice;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * JustForTest
 * <p>
 *
 * @author hyl
 * @version v1.0: JustForTest.java, v 0.1 2020/10/13 22:20 $
 */
public class JustForTest {

    public static void main(String[] args)  {

       final Lock  lock = new ReentrantLock();


       Thread thread1 = new Thread(()->{

           lock.lock();
           try {
               while (true){

               }
           }finally {
               lock.unlock();
           }
       });

        Thread thread2 = new Thread(()->{

            lock.lock();

            try {
                System.out.println("获得锁");
            }finally {
                lock.unlock();
            }
        });


        thread1.start();

        thread2.start();



    }

}
