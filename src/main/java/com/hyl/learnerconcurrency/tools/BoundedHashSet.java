package com.hyl.learnerconcurrency.tools;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 使用信号量{@link Semaphore}做有界队列
 * <p>
 *
 * @author hyl
 * @version v1.0: BoundedHashSet.java, v 0.1 2020/10/12 19:36 $
 */
public class BoundedHashSet<T> {

    private final Set<T>set;
    private final Semaphore sem;

    public BoundedHashSet(int bound){
        this.set = Collections.synchronizedSet(new HashSet<>());
        sem = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException{
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        }finally {
            if (!wasAdded)
                sem.release();
        }
    }

    public boolean remove (T o){
        boolean wasRemoved = set.remove(o);
        if (wasRemoved)
            sem.release();
        return wasRemoved;
    }

    public static void main(String[] args) {
        BoundedHashSet<Integer> boundedHashSet = new BoundedHashSet<>(2);
        Random random = new Random();
        Runnable addRun = ()->{
            int time = random.nextInt(10);
            try {
                boundedHashSet.add(time);
                System.out.println(Thread.currentThread().getName()+": "+time);

                boundedHashSet.remove(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable removeRun = ()->{
            int time = random.nextInt(10);
            try {
                boundedHashSet.add(time);
                System.out.println(Thread.currentThread().getName()+": "+time);

                boundedHashSet.remove(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 4; i++) {
             new Thread(addRun,"add-thread-"+i).start();
        }

        for (int i = 0; i < 4; i++) {
            new Thread(removeRun,"add-thread-"+i).start();
        }

    }

}
