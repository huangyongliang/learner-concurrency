package com.hyl.learnerconcurrency.practice.test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.hyl.learnerconcurrency.practice.ThreadGate;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: ThreadGateTest.java, v 0.1 2020/10/19 18:16 $
 */
public class ThreadGateTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadGate gate = new ThreadGate();
        AtomicLong count = new AtomicLong();

        Runnable runnable = ()->{
            System.out.println(Thread.currentThread().getName()+":启动");
            while (true){
                try {
                    gate.await();
                    count.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                System.out.println(Thread.currentThread().getName()+": 运行中 -> "+count.get());
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }

        TimeUnit.SECONDS.sleep(1);
        boolean flag = true;

        for (;;){
            if (flag){
                flag = false;
                gate.open();
            }else {
                flag = true;
                gate.close();
            }
            TimeUnit.SECONDS.sleep(1);
        }


    }

}
