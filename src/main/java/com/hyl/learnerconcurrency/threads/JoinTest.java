package com.hyl.learnerconcurrency.threads;

import java.util.concurrent.TimeUnit;

/**
 * join 方法测试
 * @author hyl
 * @version v1.0: JoinTest.java, v 0.1 2020/9/23 20:20 $
 */
public class JoinTest {

    public static void main(String[] args) throws InterruptedException {
        Thread preThread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new JoinRunner(preThread),String.valueOf(i));
            thread.start();
            preThread = thread;
        }

        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName()+" terminate.");
    }


    static class JoinRunner implements Runnable{

       private Thread preThread;

       public JoinRunner(Thread preThread){
           this.preThread = preThread;
       }

        @Override
        public void run() {
            try {
                preThread.join();
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName()+" terminate.");
        }
    }
}
