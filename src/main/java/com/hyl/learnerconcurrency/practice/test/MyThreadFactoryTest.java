package com.hyl.learnerconcurrency.practice.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hyl.learnerconcurrency.practice.MyAppThread;
import com.hyl.learnerconcurrency.practice.MyThreadFactory;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: MyThreadFactoryTest.java, v 0.1 2020/10/14 21:24 $
 */
public class MyThreadFactoryTest {

    public static void main(String[] args) {

        // ExecutorService executorService = Executors.newFixedThreadPool(10, new MyThreadFactory("myPool"));
        ExecutorService executorService = Executors.newFixedThreadPool(10, MyAppThread::new);


        executorService.execute(()->{
            System.out.println(Thread.currentThread().getName());
        });

        executorService.shutdown();
    }

}
