package com.hyl.learnerconcurrency.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition测试用例，有界队列
 *
 * @author hyl
 * @version v1.0: BoundedQueue.java, v 0.1 2020/9/28 4:21 $
 */
public class BoundedQueue<T> {

    private Object[] items;
    // 添加的下标，删除的下标和数组当前数量
    private int addIndex, removeIndex, count;
    private Lock lock = new ReentrantLock();
    private Condition notEmpty = lock.newCondition();
    private Condition notFull = lock.newCondition();

    public BoundedQueue(int size){
        items = new Object[size];
    }

    /**
     *  添加一个元素，如果数组满，则添加线程进入等待状态，直到有“空位”
     * @param t
     * @throws InterruptedException
     */
    public void add(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count==items.length){
                System.out.println(Thread.currentThread().getName()+":队列已【满】，【开始】等待");
                notFull.await();
                System.out.println(Thread.currentThread().getName()+":队列已【满】，【结束】等待");
            }
            items[addIndex] = t;
            // 判断是否越界，循环使用
            if (++addIndex == items.length){
                addIndex = 0;
            }
            ++count;
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 由头部删除一个元素，如果数组为空，则删除线程进入等待状态，直到有新添加元素
     * @return
     * @throws InterruptedException
     */
    @SuppressWarnings("unchecked")
    public T remove() throws InterruptedException {
        lock.lock();
        try {
            while (count==0){
                System.out.println(Thread.currentThread().getName()+":队列已【空】，【开始】等待");
                notEmpty.await();
                System.out.println(Thread.currentThread().getName()+":队列已【空】，【结束】等待");
            }
            Object x = items[removeIndex];
            // 判断是否越界，循环使用
            if (++removeIndex == items.length){
                removeIndex = 0;
            }
            --count;
            notFull.signal();
            return (T)x;
        }finally {
            lock.unlock();
        }

    }


}
