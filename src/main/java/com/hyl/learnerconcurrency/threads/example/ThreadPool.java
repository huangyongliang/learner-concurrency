package com.hyl.learnerconcurrency.threads.example;

/**
 * 线程池接口
 * @author hyl
 * @version v1.0: ThreadPool.java, v 0.1 2020/9/24 4:19 $
 */
public interface ThreadPool<Job extends Runnable> {
    /**
     * 执行一个 Job，这个 Job 需要实现 Runnable
     * @param job
     */
    void execute( Job job);

    /**
     * 关闭线程池
     */
    void shutdown();

    /**
     * 增加工作者线程
     * @param num
     */
    void addWorkers(int num);

    /**
     * 减少工作线程
     * @param num
     */
    void removeWorkers(int num);

    /**
     * 得到正在等待执行的任务数量
     * @return
     */
    int getJobSize();
}
