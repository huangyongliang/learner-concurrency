package com.hyl.learnerconcurrency.practice.state;

/**
 * 使用了条件队列实现的有界缓存
 * <p>
 *
 * @author hyl
 * @version v1.0: BoundedBuffer.java, v 0.1 2020/10/19 15:19 $
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

    // 条件谓词：not-full (!isFull)
    // 条件谓词：not-empty (isEmpty)

    protected BoundedBuffer(int capacity) {
        super(capacity);
    }


    // 阻塞并直到：not-full
    public synchronized void put(V v) throws InterruptedException {
        while (isFull())
            wait();
        // 条件通知，当时从 Empty 变为 非Empty 时通知
        boolean wasEmpty = isEmpty();
        doPut(v);
        if (wasEmpty)
            notifyAll();
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty())
            wait();
        // 条件通知，当时从 Full 变为 非Full 时通知
        boolean wasFull = isFull();
        V v = doTake();
        if (wasFull)
            notifyAll();
        return v;
    }

}
