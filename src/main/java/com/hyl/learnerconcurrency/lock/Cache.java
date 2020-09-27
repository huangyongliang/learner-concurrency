package com.hyl.learnerconcurrency.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁缓存例子
 *
 * @author hyl
 * @version v1.0: Cache.java, v 0.1 2020/9/27 1:44 $
 */
public class Cache {

    static Map<String,Object> map = new HashMap<>();
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();

    /**
     * 获取一个 key 对应的 value
     * @param key
     * @return
     */
    public static final Object get(String key){
        r.lock();
        try {
            return map.get(key);
        }finally {
            r.unlock();
        }
    }

    /**
     * 设置 key 对应的 value，并返回旧的 value
     * @param key
     * @param value
     * @return
     */
    public static final Object put(String key, Object value){
        w.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"写锁："+rwl.getWriteHoldCount());
           return map.put(key, value);
        }finally {
            w.unlock();
        }
    }

    /**
     * 清空所有的内容
     */
    public static final void clear(){
        w.lock();
        try {
            map.clear();
        }finally {
            w.unlock();
        }
    }

    public static final String toStr(){

        r.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"读锁："+rwl.getReadHoldCount());
            return  map.size() +": "+  map.toString();
        }finally {
            r.unlock();
        }
    }
    public static final String toStr2(){

        r.lock();
        r.lock();
        try {
            System.out.println(Thread.currentThread().getName()+"读锁："+rwl.getReadHoldCount());
            return  map.size() +": "+  map.toString();
        }finally {
            r.unlock();
            r.unlock();
        }
    }

    public static int getWriteHeldCount(){
        return rwl.getWriteHoldCount();
    }

}
