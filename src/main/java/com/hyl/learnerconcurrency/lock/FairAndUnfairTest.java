package com.hyl.learnerconcurrency.lock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * 公平锁和非公平锁 重入锁
 *
 * @author hyl
 * @version v1.0: FairAndUnfairTest.java, v 0.1 2020/9/26 22:57 $
 */
public class FairAndUnfairTest {


    private static final Lock fairLock = new ReentrantLock2(true);
    private static final Lock unfairLock = new ReentrantLock2(false);


    @Test
    public void fair(){
        testLock(fairLock);
    }

    @Test
    public void unfair(){
        testLock(unfairLock);
    }

    private void testLock(Lock lock){

        for (int i = 0; i < 5; i++) {
            Job job = new Job(lock);
            job.start();
        }

        SleepUtils.second(22);
    }


    private static class Job extends Thread{

        private Lock lock;

        public Job(Lock lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 2; i++) {
                lock.lock();
                try {
                    System.out.print(Thread.currentThread().getName() +"\t" );

                    if (lock instanceof ReentrantLock2){
                        System.out.print("等待线程：");
                        ((ReentrantLock2)lock).getQueuedThreads().forEach((Thread thread)->{
                            System.out.print(thread.getName()+"\t");
                        });
                        System.out.println();
                        SleepUtils.second(2);
                    }
                }finally {
                    lock.unlock();
                }

            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock{

        public ReentrantLock2 (boolean fair){
            super(fair);
        }

        @Override
        protected Collection<Thread> getQueuedThreads() {
            List<Thread> arrayList = new ArrayList<>(super.getQueuedThreads());
            Collections.reverse(arrayList);
            return arrayList;
        }
    }

}
