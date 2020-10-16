package com.hyl.learnerconcurrency.practice.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.hyl.learnerconcurrency.lock.BoundedQueue;
import com.hyl.learnerconcurrency.practice.BoundedBuffer;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: PutTakeTest.java, v 0.1 2020/10/16 15:01 $
 */
public class PutTakeTest {
    private static final ExecutorService pool = Executors.newCachedThreadPool();
    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);
    private final CyclicBarrier barrier;
    private final BarrierTimer timer;
    private final BoundedBuffer<Integer> bb;
    // private final BlockingQueue<Integer> bb;
    private final int nTrials, nPairs;

    public PutTakeTest(int capacity, int nPairs, int nTrials){
        this.bb = new BoundedBuffer<>(capacity);
        this.nTrials = nTrials;
        this.nPairs = nPairs;
        this.timer = new BarrierTimer();
        this.barrier = new CyclicBarrier(nPairs*2 + 1,timer);
    }

    void test(){
        try {
            timer.clear();
            for (int i = 0 ; i< nPairs;i++){
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
            // 等待所有的线程都就绪
            barrier.await();
            // 等待所有的线程执行完成
            barrier.await();
            long nsPerItem = timer.getTime() / (nPairs * (long)nTrials);
            System.out.println("Throughput: "+ nsPerItem + " ns/item");
            if (putSum.get() == takeSum.get()){
                System.out.println("test success");
            }else {
                System.out.println("test fail");
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    /**
     * 适合在测试中使用的随机数生成器
     * @param y
     * @return
     */
    static int xorShift(int y){
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }


    class Producer implements Runnable{
        @Override
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int)System.nanoTime());
                int sum = 0 ;
                barrier.await();
                for (int i = nTrials; i >0 ; i--) {
                    bb.put(seed);
                    sum +=seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                barrier.await();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    class Consumer implements Runnable{
        @Override
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = nTrials; i> 0;i--){
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            }catch (Exception e){
                throw  new RuntimeException(e);
            }
        }
    }

    class BarrierTimer implements Runnable{

        private boolean started;
        private long startTime,endTime;

        @Override
        public synchronized void run() {
            long t = System.nanoTime();
            if (!started){
                started = true;
                startTime = t;
            }else
                endTime = t;
        }

        public synchronized void clear(){
            started =false;
        }

        public synchronized  long getTime(){
            return endTime - startTime;
        }

    }

    public static void main(String[] args) {
        int n = Runtime.getRuntime()
            .availableProcessors();
        new PutTakeTest(10000, n,1000000).test();
        pool.shutdown();
    }

}
