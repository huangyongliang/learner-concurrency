package com.hyl.learnerconcurrency.threads.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 默认线程池实现
 *
 * @author hyl
 * @version v1.0: DefaultThreadPool.java, v 0.1 2020/9/24 4:24 $
 */
public class DefaultThreadPool<Job extends Runnable> implements ThreadPool<Job> {
    // 线程池最大限制数
    private static final int MAX_WORKERS_NUMBERS = 10;
    // 线程池默认的数量
    private static final int DEFAULT_WORKERS_NUMBERS = 5;
    // 线程池最小的数量
    private static final int MIN_WORKERS_NUMBERS = 1;
    // 这时一个工作列表，将会向里面插入工作
    private final LinkedList<Job> jobs = new LinkedList<>();
    // 工作者列表
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());
    // 工作者线程的数量
    private int workerNum = DEFAULT_WORKERS_NUMBERS;
    // 线程编号生成
    private AtomicLong threadNum = new AtomicLong();

    public DefaultThreadPool() {
        initializeWorkers(DEFAULT_WORKERS_NUMBERS);
    }

    public DefaultThreadPool(int num) {
        workerNum = num > MAX_WORKERS_NUMBERS ? MAX_WORKERS_NUMBERS : Math.max(num, MIN_WORKERS_NUMBERS);
        initializeWorkers(workerNum);
    }

    @Override
    public void execute(Job job) {
        if (job!=null){
            synchronized (jobs){
                // 添加一个工作，然后经行通知
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker: workers){
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs){
            // 限制新增的Worker数量不能超过最大值
            if (num+this.workerNum>MAX_WORKERS_NUMBERS){
                num = MAX_WORKERS_NUMBERS - this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum+=num;
        }
    }

    @Override
    public void removeWorkers(int num) {
        synchronized (jobs){

            if (num>=this.workerNum){
                throw new IllegalArgumentException("beyond workNum");
            }

            if (num<0){
                throw new IllegalArgumentException("num is negative");
            }

            // 按照给定的数量停止 Worker
            int count = 0;
            while (count<num){
                Worker worker = workers.get(count);
                if (workers.remove(worker)){
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum-=num;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    private void initializeWorkers(int num) {
        for (int i = 0; i < num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    class Worker implements Runnable {
        // 是否工作
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            // 感知到外部对 WorkerThread 的中断操作，返回
                            Thread.currentThread()
                                .interrupt();
                            return;
                        }
                    }
                    // 取出一个Job
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {
                        // Job的异常，这里忽略了
                        e.printStackTrace();
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}
