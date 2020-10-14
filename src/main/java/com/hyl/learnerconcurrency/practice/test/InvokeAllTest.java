package com.hyl.learnerconcurrency.practice.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * {@link ExecutorService#invokeAll(java.util.Collection, long, java.util.concurrent.TimeUnit)} 测试类
 * <p>
 *
 * @author hyl
 * @version v1.0: InvokeAllTest.java, v 0.1 2020/10/13 20:56 $
 */
public class InvokeAllTest {

    static Random random = new Random();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<UpdateRunnable> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new UpdateRunnable());
        }

        List<Future<Integer>> futureList = executorService.invokeAll(list, 5, TimeUnit.SECONDS);

        int suCount = 0, failCount = 0, exeCount = 0, interCount = 0;
        for (Future<Integer> future : futureList) {

            try {
                int time = future.get();
                System.out.println("执行成功：" + time + "s");
                suCount++;
            } catch (ExecutionException e) {
                exeCount++;
            } catch (CancellationException e) {
                failCount++;
            } catch (InterruptedException e) {
                interCount++;
            }
        }
        System.out.println("执行成功：" + suCount + "，取消任务：" + failCount + "，异常任务：" + exeCount + "，中断任务：" + interCount);

        executorService.shutdown();
    }

    static class UpdateRunnable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            int time = random.nextInt(10);
            System.out.println(Thread.currentThread()
                .getName() + ":run " + time + "s");
            Thread.sleep(TimeUnit.SECONDS.toMillis(time));
            return time;
        }
    }
}
