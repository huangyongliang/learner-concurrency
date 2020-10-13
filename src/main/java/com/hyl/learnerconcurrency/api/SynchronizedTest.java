package com.hyl.learnerconcurrency.api;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: SynchronizedTest.java, v 0.1 2020/10/11 19:18 $
 */
public class SynchronizedTest {

    public static void main(String[] args) {


        Test test = new Test();

        Thread thread = new Thread(()->{

            synchronized (Test.class){
                while (true)
                    ;
            }

        },"zuse-thread");

        thread.start();

        SleepUtils.second(1);

        Test.print();


    }


   static class Test{
        public static synchronized void print(){
            System.out.println("进来了");
        }

    }
}
