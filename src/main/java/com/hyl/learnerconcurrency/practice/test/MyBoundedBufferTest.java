package com.hyl.learnerconcurrency.practice.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.hyl.learnerconcurrency.common.SleepUtils;
import com.hyl.learnerconcurrency.practice.BoundedBuffer;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: BoundedBufferTest.java, v 0.1 2020/10/16 10:56 $
 */
public class MyBoundedBufferTest {
    public static void main(String[] args) {

        BoundedBuffer<Integer> buffer = new BoundedBuffer<>(2);

        Random random = new Random();

        Runnable putRun = ()->{
            while (true){
                if (!buffer.isFull()){
                    try {
                        int put =random.nextInt(10);
                        buffer.put(put);
                        System.out.println(Thread.currentThread().getName()+":put "+put);
                        SleepUtils.second(put);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        };

        Runnable takeRun = ()->{
            while (true){
                if (!buffer.isEmpty()){
                    try {
                        Integer take = buffer.take();
                        System.out.println(Thread.currentThread().getName()+":take "+take);
                        SleepUtils.second(take);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
        };

        ExecutorService putService = Executors.newFixedThreadPool(2);
        ExecutorService takeService = Executors.newSingleThreadExecutor();

        putService.execute(putRun);
        putService.execute(putRun);
        takeService.execute(takeRun);

    }
}
