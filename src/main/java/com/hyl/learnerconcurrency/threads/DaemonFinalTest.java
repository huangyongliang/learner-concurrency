package com.hyl.learnerconcurrency.threads;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * @author hyl
 * @version v1.0: DeamonFinalTest.java, v 0.1 2020/9/22 18:17 $
 */
public class DaemonFinalTest {

    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                SleepUtils.second(10);
            } finally {
                // 不一定执行
                System.out.println("DaemonThread finally run.");
            }
        }
    }

}
