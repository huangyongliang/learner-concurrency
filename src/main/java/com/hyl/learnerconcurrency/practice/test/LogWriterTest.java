package com.hyl.learnerconcurrency.practice.test;

import java.io.PrintWriter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.hyl.learnerconcurrency.common.SleepUtils;
import com.hyl.learnerconcurrency.practice.LogWriter;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: LogWriterTest.java, v 0.1 2020/10/14 13:59 $
 */
public class LogWriterTest {

    static int count = 0;

    public static void main(String[] args) {
        PrintWriter  writer =  new PrintWriter(System.out);
        LogWriter logWriter = new LogWriter(writer);
        logWriter.start();

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

        scheduledExecutorService.scheduleAtFixedRate(()->{
            try {
                logWriter.log("123");
                count++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },0,1000, TimeUnit.MILLISECONDS);

        SleepUtils.second(10);
        // logWriter.stop();
        writer.flush();
        scheduledExecutorService.shutdown();

        System.exit(0);
    }
}
