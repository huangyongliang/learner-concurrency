package com.hyl.learnerconcurrency.practice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 在 ExecutorService 中跟踪在关闭之后呗取消的任务
 * <p>
 *
 * @author hyl
 * @version v1.0: TrackingExecutor.java, v 0.1 2020/10/14 16:56 $
 */
public class TrackingExecutor extends AbstractExecutorService {

    private final ExecutorService exec;

    private final Set<Runnable> tasksCancelledAtShutdown = Collections.synchronizedSet(new HashSet<>());

    public TrackingExecutor(){
        exec = Executors.newCachedThreadPool();
    }

    public TrackingExecutor(ExecutorService exec){
        this.exec = exec;
    }


    @Override
    public void shutdown() {
        exec.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return exec.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return exec.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return exec.isShutdown();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return exec.awaitTermination(timeout,unit);
    }

    @Override
    public void execute(Runnable command) {
        exec.execute(() -> {
            try {
                command.run();
            }finally {
                if (isShutdown() && Thread.currentThread().isInterrupted())
                    tasksCancelledAtShutdown.add(command);
            }
        });
    }

    public List<Runnable> getCancelledTasks(){
        if (!exec.isTerminated())
            throw new IllegalStateException();
        return new ArrayList<>(tasksCancelledAtShutdown);
    }
}
