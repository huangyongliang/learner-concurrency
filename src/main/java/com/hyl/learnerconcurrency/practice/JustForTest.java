package com.hyl.learnerconcurrency.practice;

/**
 * JustForTest
 * <p>
 *
 * @author hyl
 * @version v1.0: JustForTest.java, v 0.1 2020/10/13 22:20 $
 */
public class JustForTest {

    public static void main(String[] args)  {

        Object a = new Object();
        Object b = new Object();

        Runnable r1 = ()->{
            synchronized (a){
                synchronized (b){
                    try {
                       b.wait();
                       a.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        Runnable r2 = ()->{
            synchronized (a){
                System.out.println("进入 锁 a");
            }
        };

        Runnable r3 = ()->{
            synchronized (b){
                System.out.println("进入 锁 b");
            }
        };
        Thread thread1 = new Thread(r1);
        Thread thread2 = new Thread(r2);
        Thread thread3 = new Thread(r3);

        thread1.start();
        thread2.start();
        thread3.start();

    }

}
