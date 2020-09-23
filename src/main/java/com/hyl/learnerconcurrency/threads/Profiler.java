package com.hyl.learnerconcurrency.threads;

import java.util.concurrent.TimeUnit;

/**
 * @author hyl
 * @version v1.0: Profiler.java, v 0.1 2020/9/24 2:06 $
 */
public class Profiler {

    private static final ThreadLocal<Long> TIME_THREAD_LOCAL = ThreadLocal.withInitial(System::currentTimeMillis);


    public static void begin(){
        TIME_THREAD_LOCAL.set(System.currentTimeMillis());
    }

    public static long end(){
        return System.currentTimeMillis() - TIME_THREAD_LOCAL.get();
    }

    public static void main(String[] args) throws InterruptedException {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + Profiler.end() + " mills");
    }

}
