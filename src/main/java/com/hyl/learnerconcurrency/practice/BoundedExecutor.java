package com.hyl.learnerconcurrency.practice;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

import org.apache.catalina.Executor;

/**
 * 使用信号量{@link Semaphore}控制任务的提交速率
 * <p>
 *
 * @author hyl
 * @version v1.0: BoundedExecutor.java, v 0.1 2020/10/14 20:33 $
 */
public class BoundedExecutor {

    private final Executor exec;
    private final Semaphore semaphore;

    public BoundedExecutor(Executor exec, int bound){
        this.exec = exec;
        this.semaphore = new Semaphore(bound);
    }

    public void submitTask(final Runnable command) throws InterruptedException{

        semaphore.acquire();
        try {
            exec.execute(() -> {
                try {
                    command.run();
                }finally {
                    semaphore.release();
                }
            });
        }catch (RejectedExecutionException e){
            semaphore.release();
        }


    }


}
