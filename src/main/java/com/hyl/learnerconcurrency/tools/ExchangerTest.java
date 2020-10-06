package com.hyl.learnerconcurrency.tools;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * {@link java.util.concurrent.Exchanger} 测试类
 * <p>
 *
 * @author hyl
 * @version v1.0: ExchangerTest.java, v 0.1 2020/10/6 8:06 $
 */
public class ExchangerTest {

    private static final Exchanger<String> exgr = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {

        threadPool.execute(() -> {
            String A = "银行流水A";
            try {
                String B = exgr.exchange(A);
                System.out.println(Thread.currentThread()
                    .getName() + "：A拿到了B录入的：" + B);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.execute(() -> {
            String B = "银行流水B";
            try {

                String A = exgr.exchange(B);
                System.out.println(Thread.currentThread()
                    .getName() + "：A和B数据是否一致：" + A.equals(B) + "，A录入的是：" + A + "，B录入的是：" + B);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threadPool.shutdown();

    }

}
