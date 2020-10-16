package com.hyl.learnerconcurrency.practice;

/**
 * 典型条件下会发生死锁的循环
 * <p>
 *
 * @author hyl
 * @version v1.0: DemonstrateDeadlock.java, v 0.1 2020/10/15 16:18 $
 */
public class DemonstrateDeadlock {

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();


        Thread threadA = new Thread(()->{
            while (true){
                synchronized (a){
                    synchronized (b){
                        System.out.println(Thread.currentThread().getName());
                    }
                }
            }
        },"thread - a");

        Thread threadB = new Thread(()->{
            while (true){
                synchronized (b){
                    synchronized (a){
                        System.out.println(Thread.currentThread().getName());
                    }
                }
            }
        },"thread - b");

        threadA.start();
        threadB.start();


    }


}
