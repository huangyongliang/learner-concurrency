package com.hyl.learnerconcurrency.practice.state;

/**
 * 当不满足前提条件时，有界缓存不会执行响应的操作（不建议日常使用）
 * <p>
 *
 * @author hyl
 * @version v1.0: GrumpyBoundedBuffer.java, v 0.1 2020/10/19 13:54 $
 */
public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {
    protected GrumpyBoundedBuffer(int capacity) {
        super(capacity);
    }

    public synchronized void put(V v) throws BufferFullException {
        if (isFull())
            throw new BufferFullException();
        doPut(v);
    }

    public synchronized V take() throws BufferEmptyException {
        if (isEmpty())
            throw new BufferEmptyException();
        return doTake();
    }

}
