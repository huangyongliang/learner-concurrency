package com.hyl.learnerconcurrency.threads;

import java.util.concurrent.TimeUnit;

import com.hyl.learnerconcurrency.common.SleepUtils;
import com.hyl.learnerconcurrency.common.ThreadUtils;

/**
 * @author hyl
 * @version v1.0: Interrupted.java, v 0.1 2020/9/22 23:00 $
 */
public class Interrupted {


    public static void main(String[] args) throws InterruptedException {
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);
        sleepThread.start();
        busyThread.start();

        // 休眠 5 秒，让 sleepThread 和 busyThread 充分运行
        TimeUnit.SECONDS.sleep(5);
        ThreadUtils.printAllThreadState();

        sleepThread.interrupt();
        busyThread.interrupt();
        SleepUtils.second(2);
        System.out.println("SleepThread interrupted is " + sleepThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());
        System.out.println("BusyThread interrupted is " + busyThread.isInterrupted());

        // 防止 sleepThread 和 busyThread 立刻退出
        SleepUtils.second(20);

        ThreadUtils.printAllThreadState();



    }

    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                SleepUtils.second(10);
            }
        }
    }

    static class BusyRunner implements Runnable {

        @Override
        public void run() {
            while (true) {

            }
        }
    }

}
