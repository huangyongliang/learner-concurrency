package com.hyl.learnerconcurrency.common;

import java.util.concurrent.TimeUnit;

/**
 * @author hyl
 * @version v1.0: SleepUtils.java, v 0.1 2020/9/22 16:09 $
 */
public abstract class SleepUtils {

    public static void second(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
