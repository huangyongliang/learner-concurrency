package com.hyl.learnerconcurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * @author hyl
 * @version v1.0: LockJustTest.java, v 0.1 2020/9/25 1:43 $
 */
public class LockJustTest {

    private static volatile  boolean flag = true;

    public static void main(String[] args) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2));

        System.out.println(TimeUnit.SECONDS.toNanos(2));

    }

    static class RunLockSupport implements Runnable{

        private Thread parentThread;

        public RunLockSupport(Thread parentThread){
            this.parentThread = parentThread;
        }

        @Override
        public void run() {
            SleepUtils.second(4);
            LockSupport.unpark(parentThread);
        }
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
