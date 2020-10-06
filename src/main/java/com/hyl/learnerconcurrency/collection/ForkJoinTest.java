package com.hyl.learnerconcurrency.collection;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import org.junit.Test;

/**
 * Fork/Join测试
 * <p>
 * @author hyl
 * @version v1.0: ForkJoinTest.java, v 0.1 2020/10/5 4:59 $
 */
public class ForkJoinTest {

    final static long count = 10000;

    @Test
    public  void run() {

        ForkJoinPool forkJoinPool = new ForkJoinPool(8*200);

        CountTask task = new CountTask(1,count);

        Future<Long> result = forkJoinPool.submit(task);

        try {
            System.out.println(result.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void diffRun(){

        long sum = 0;

        for (long i = 1; i <=count; i++) {
            sum+=i;
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(sum);
    }


    static class CountTask extends RecursiveTask<Long>{

        // 阈值
        private static final int THRESHOLD = 2;

        private long start;
        private long end;

        public CountTask(long start, long end){
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {

            long sum = 0;

            // 如果任务足够小就计算任务
            boolean canCompute = (end - start) <= THRESHOLD;
            if (canCompute){
                for (long i = start; i <= end; i++) {
                    sum += i;
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }else {
                // 如果任务大于阈值，就分裂成两个子任务计算
                long middle = (start+end) / 2;
                CountTask leftTask = new CountTask(start,middle);
                CountTask rightTask = new CountTask(middle+1,end);
                // 执行子任务
                leftTask.fork();
                rightTask.fork();
                // 等待子任务执行完，并得到其结果
                long leftResult = leftTask.join();
                long rightResult = rightTask.join();
                // 合并子任务
                sum = leftResult + rightResult;
            }

            return sum;
        }
    }


}
