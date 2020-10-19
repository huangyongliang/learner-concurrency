package com.hyl.learnerconcurrency.practice.state;

/**
 * 有界缓存实现的基类
 * <p>
 *
 * @author hyl
 * @version v1.0: BaseBoundedBuffer.java, v 0.1 2020/10/19 13:43 $
 */
public abstract class BaseBoundedBuffer<V> {
    private final V[] buf;
    private int tail;
    private int head;
    private int count;

    protected BaseBoundedBuffer(int capacity){
        this.buf = (V[]) new Object[capacity];
    }

    protected synchronized final void doPut( V v){
        buf[tail] = v;
        if (++tail == buf.length)
            tail = 0;
        ++count;
    }

    protected synchronized final V doTake(){
        V v = buf[head];
        if (++head == buf.length)
            head = 0;
        --count;
        return v;
    }

    public synchronized final boolean isFull(){
        return count == buf.length;
    }

    public synchronized final boolean isEmpty(){
        return count == 0;
    }

}
