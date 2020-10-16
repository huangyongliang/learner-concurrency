package com.hyl.learnerconcurrency.practice.puzzle;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁
 * <p>
 *
 * @author hyl
 * @version v1.0: ValueLatch.java, v 0.1 2020/10/15 2:01 $
 */
public class ValueLatch<T> {
    private T value = null;
    private final CountDownLatch done = new CountDownLatch(1);

    public boolean isSet(){
        return done.getCount() == 0;
    }

    public synchronized void setValue(T newValue){
        if (!isSet()){
            value = newValue;
            done.countDown();
        }
    }

    public T getValue() throws InterruptedException{
        done.await();
        synchronized (this){
            return value;
        }
    }

}
