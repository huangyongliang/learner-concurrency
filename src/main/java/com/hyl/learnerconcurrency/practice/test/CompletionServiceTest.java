package com.hyl.learnerconcurrency.practice.test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * {@link ExecutorCompletionService}测试类
 * <p>
 *
 * @author hyl
 * @version v1.0: CompletionServiceTest.java, v 0.1 2020/10/13 18:03 $
 */
public class CompletionServiceTest {

   static   Random random = new Random();

    public static void main(String[] args) throws InterruptedException, ExecutionException {


        ExecutorService executorService = Executors.newFixedThreadPool(10);

        CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService);


        for (int i = 0; i < 10; i++) {
           completionService.submit(new UpdateRunnable());
        }

        for (int i = 0; i < 10; i++) {
            Future<Integer> take = completionService.take();
            int time = take.get();
            System.out.println(Thread.currentThread().getName()+":run "+time+"s");
        }

        executorService.shutdown();
    }


    static class UpdateRunnable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int time = random.nextInt(8);
            System.out.println(Thread.currentThread().getName()+":run "+time+"s");
            TimeUnit.SECONDS.sleep(time);
            return time;
        }
    }

}
