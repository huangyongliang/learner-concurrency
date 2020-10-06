package com.hyl.learnerconcurrency.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: CyclicBarrierBroken.java, v 0.1 2020/10/6 7:19 $
 */
public class CyclicBarrierBroken {

    static CyclicBarrier c = new CyclicBarrier(2);

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                c.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        thread.start();
        thread.interrupt();
        System.out.println(c.isBroken());
        try {
            c.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            System.out.println(c.isBroken());
            e.printStackTrace();
        }



    }

}
