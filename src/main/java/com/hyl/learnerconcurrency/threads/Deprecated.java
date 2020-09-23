package com.hyl.learnerconcurrency.threads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * @author hyl
 * @version v1.0: Deprecated.java, v 0.1 2020/9/23 0:40 $
 */
public class Deprecated {
    public static void main(String[] args) throws InterruptedException {

        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        Thread printThread = new Thread(new Runner(), "PrintThread");
        printThread.setDaemon(true);
        printThread.start();
        TimeUnit.SECONDS.sleep(3);

        // 暂停
        printThread.suspend();
        System.out.println("main suspend PrintThread at " + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);

        // 恢复
        printThread.resume();
        System.out.println("main resume PrintThread at " + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);

        // 停止
        printThread.stop();
        System.out.println("main stop PrintThread at " + format.format(new Date()));
        TimeUnit.SECONDS.sleep(3);

    }

    static class Runner implements Runnable {
        @Override
        public void run() {
            DateFormat format = new SimpleDateFormat("HH:mm:ss");
            while (true) {
                System.out.println(Thread.currentThread()
                    .getName() + " Run at " + format.format(new Date()));
                SleepUtils.second(1);
            }
        }
    }

}
