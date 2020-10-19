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
        doPut(v);
        notifyAll();
    }

    public synchronized V take() throws InterruptedException {
        while (isEmpty())
            wait();
        V v = doTake();
        notifyAll();
        return v;
    }

}
