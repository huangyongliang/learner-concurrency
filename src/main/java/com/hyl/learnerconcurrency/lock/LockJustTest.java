package com.hyl.learnerconcurrency.lock;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * @author hyl
 * @version v1.0: LockJustTest.java, v 0.1 2020/9/25 1:43 $
 */
public class LockJustTest {

    private static volatile  boolean flag = true;

    public static void main(String[] args) {

        Thread thread = new Thread(new Runner(),"TestThread");
        thread.start();
        SleepUtils.second(1);
        flag =false;

    }

    static class Runner implements Runnable{

        private long count = 0;
        @Override
        public void run() {
            while (flag){
                count++;
            }
            System.out.println(count);
        }
    }

}
