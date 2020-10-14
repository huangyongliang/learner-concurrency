package com.hyl.learnerconcurrency.practice;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: CancellableTask.java, v 0.1 2020/10/14 12:57 $
 */
public interface CancellableTask<T> extends Callable<T> {
    void cancel();
    RunnableFuture<T> newTask();
}
