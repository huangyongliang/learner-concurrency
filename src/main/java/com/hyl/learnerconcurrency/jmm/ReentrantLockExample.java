package com.hyl.learnerconcurrency.jmm;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hyl
 * @version v1.0: ReentrantLockExample.java, v 0.1 2020/9/21 16:09 $
 */
public class ReentrantLockExample {

    int a = 0;
    ReentrantLock lock = new ReentrantLock();

    public void writer(){
        lock.lock();
        try {
            a++;
        }finally {
            lock.unlock();
        }
    }

    public void reader(){
        lock.lock();
        try {
            int i = a;
            System.out.println(i);
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockExample example = new ReentrantLockExample();

        Thread thread1 = new Thread(example::writer);
        Thread thread2 = new Thread(example::reader);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }

}
