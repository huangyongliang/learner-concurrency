package com.hyl.learnerconcurrency.practice;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 自定义线程池线程类
 * <p>
 *
 * @author hyl
 * @version v1.0: MyAppThread.java, v 0.1 2020/10/14 21:09 $
 */
public class MyAppThread extends Thread{
    public static final String DEFAULT_NAME = "MyAppThread";
    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    private static final Logger log = Logger.getAnonymousLogger();

    public MyAppThread(Runnable r){
        this(r,DEFAULT_NAME);
    }

    public MyAppThread(Runnable r, String name){
        super(r,name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler((t, e) -> log.log(Level.SEVERE, "UNCAUGHT in thread "+ t.getName(),e));
    }

    @Override
    public void run() {
        // 复制 debug 标志以确保一致的值
        boolean debug= debugLifecycle;
        if (debug){
            log.log(Level.FINE,"Created "+getName());
        }
        try {
            alive.incrementAndGet();
            super.run();
        }finally {
            alive.decrementAndGet();
            if (debug){
                log.log(Level.FINE,"Exiting "+getName());
            }
        }
    }

    public static int getThreadsCreated(){return created.get();}
    public static int getThreadsAlive(){return alive.get();}
    public static boolean getDebug(){return debugLifecycle;}
    public static void setDebug(boolean b){ debugLifecycle=b;}


}
