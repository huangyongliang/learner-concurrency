package com.hyl.learnerconcurrency.practice;

/**
 * 使用 wait 和 notifyAll 来实现可重新关闭的阀门
 * <p>
 *
 * @author hyl
 * @version v1.0: ThreadGate.java, v 0.1 2020/10/19 18:10 $
 */
public class ThreadGate {
    // 条件谓词：opened-since(n) (isOpen || generation > n)
    private boolean isOpen;
    private int generation;

    public synchronized void close(){
        isOpen = false;
    }

    public synchronized void open(){
        ++generation;
        isOpen = true;
        notifyAll();
    }

    /**
     *  阻塞并直到：opened-since(generation on entry)
     *
     * @throws InterruptedException
     */
    public synchronized void await() throws InterruptedException{
        int arrivalGeneration = generation;
        while (!isOpen && arrivalGeneration == generation){
            wait();
        }
    }
}
