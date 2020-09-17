package com.hyl.learnerconcurrency.challenge;

/**
 * 并发测试
 *
 * @author hyl
 * @version v1.0: ConcurrencyTest.java, v 0.1 2020/9/17 22:23 $
 */
public class ConcurrencyTest {

    private static final long count = 10000000000L;

    /**
     * 多线程 并行
     *
     * @throws InterruptedException
     */
    private static void concurrency() throws InterruptedException{

        long start = System.currentTimeMillis();

        Thread thread1 = new Thread(()->{
            long a= 0;
            for (long i = 0; i < count; i++) {
                a +=5;
            }
        });

        Thread thread2 = new Thread(()->{
            long b = 0;
            for (long i = 0; i < count; i++) {
                b--;
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
        long time = System.currentTimeMillis() - start;
        System.out.println("concurrency : "+time+"ms,b=");

    }

    /**
     *  串行，单线程
     */
    private static void serial(){
        long start = System.currentTimeMillis();

        long a = 0;
        for (long i = 0; i < count; i++) {
            a +=5;
        }

        long b = 0 ;
        for (long i = 0; i <count; i++) {
            b--;
        }
        long time = System.currentTimeMillis()-start;
        System.out.println("serial : "+time+"ms,b="+b);
    }

    public static void main(String[] args) throws InterruptedException {
        // concurrency();
        // serial();

        Thread thread1 = new Thread(()->{
            try {
                concurrency();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(ConcurrencyTest::serial);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();


    }
}
