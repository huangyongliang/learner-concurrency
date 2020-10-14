package com.hyl.learnerconcurrency.practice;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.hyl.learnerconcurrency.practice.test.TimeRunTest;

/**
 * 使用线程池，future合理的中断超时线程
 * <p>
 * {@link TimeRunTest} 测试
 * @author hyl
 * @version v1.0: TimeRunTest.java, v 0.1 2020/10/14 11:57 $
 */
public class TimeRun {

    private static ExecutorService taskExec = Executors.newFixedThreadPool(1);


    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException{

        Future<?> task = taskExec.submit(r);

        try {
            task.get(timeout,unit);
        }catch (TimeoutException e){
            e.printStackTrace();
            // 接下来任务被取消
        }catch (ExecutionException e){
            // 如果在任务中抛出了异常，那么重新抛出该异常
            throw new InterruptedException(e.getMessage());
        }finally {
            // 如果任务已经结束，那么执行取消操作也不会带来任何影响
            // 如果任务正在运行，那么将被中断
            task.cancel(true);
        }



    }



    public static void shutDown(){
        taskExec.shutdown();
    }
}
