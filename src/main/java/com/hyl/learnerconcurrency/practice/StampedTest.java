package com.hyl.learnerconcurrency.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;


/**
 * StampedLock {@link java.util.concurrent.locks.StampedLock}测试
 * <p>
 *
 * @author hyl
 * @version v1.0: StampedTest.java, v 0.1 2020/10/17 12:15 $
 */
public class StampedTest {

    private static StampedLock lock = new StampedLock();
    private static List<String> data = new ArrayList<>();

    public static void write(){
        long stamped = -1;
        try {
            stamped = lock.writeLock();
            data.add("写线程写入的数据"+stamped);
            System.out.println("写入的数据是："+stamped);
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlockWrite(stamped);
        }
    }

    public static void read(){
        long stamped = -1;
        try {
            stamped = lock.readLock();
            for (String name : data){
                System.out.println("读的数据是："+name);
            }
            TimeUnit.SECONDS.sleep(1);
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlockRead(stamped);
        }
    }

    public static void optimisticRead(){
        // 尝试去拿一个乐观锁
        long stamped = lock.tryOptimisticRead();
        //如果没有线程修改，我们再去获取一个读锁
        if (lock.validate(stamped)){
            try {
                stamped = lock.readLock();
                for (String name : data){
                    System.out.println("读的数据是："+name);
                }
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlockRead(stamped);
            }
        }

    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Runnable readTask = () -> {
            for (;;){
                // optimisticRead();
                read();
            }
        };

        Runnable writeTask = () -> {
            for (;;){
                write();
            }
        };

        for (int i = 0; i < 9; i++) {
            executorService.execute(readTask);
        }
        executorService.execute(writeTask);

    }


}
