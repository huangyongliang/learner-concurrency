package com.hyl.learnerconcurrency.practice;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂类
 * <p>
 *
 * @author hyl
 * @version v1.0: MyThreadFactory.java, v 0.1 2020/10/14 21:21 $
 */
public class MyThreadFactory implements ThreadFactory {

    private final String poolName;

    public MyThreadFactory(String poolName){
        this.poolName = poolName;
    }


    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r,poolName);
    }
}
