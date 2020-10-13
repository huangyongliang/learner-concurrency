package com.hyl.learnerconcurrency.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * {@link CyclicBarrier} 测试类
 * <p>
 *
 * @author hyl
 * @version v1.0: CyclicBarrierTest.java, v 0.1 2020/10/6 6:27 $
 */
public class CyclicBarrierTest {

    static CyclicBarrier c = new CyclicBarrier(2, () -> System.out.println("start:"));

    public static void main(String[] args) {
        new Thread(() -> {

            int count;
            try {
                count =  c.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
                count=-1;
            }
            System.out.println(Thread.currentThread().getName()+": "+count);
        }).start();

        int count;
        try {
            count = c.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
            count=-1;
        }
        System.out.println(Thread.currentThread().getName()+": "+count);
    }

}
