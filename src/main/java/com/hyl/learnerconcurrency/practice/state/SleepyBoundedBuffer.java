package com.hyl.learnerconcurrency.practice.state;

/**
 * 使用简单阻塞实现的有界缓存（不建议日常使用）
 * <p>
 *
 * @author hyl
 * @version v1.0: SleepyBoundedBuffer.java, v 0.1 2020/10/19 14:10 $
 */
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    private static final int SLEEP_GRANULARITY = 100;

    protected SleepyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public void put(V v) throws InterruptedException {
        while (true){
            synchronized (this){
                if (!isFull()){
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }

    public V take() throws InterruptedException {
        while (true){
            synchronized (this){
                if (!isEmpty())
                    return doTake();
            }
            Thread.sleep(SLEEP_GRANULARITY);
        }
    }

}
