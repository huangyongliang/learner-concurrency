package com.hyl.learnerconcurrency.practice;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.hyl.learnerconcurrency.practice.test.LogWriterTest;

/**
 * 日志类
 * <p>
 *{@link LogWriterTest}
 * @author hyl
 * @version v1.0: LogWriter.java, v 0.1 2020/10/14 13:34 $
 */
public class LogWriter {

    private final BlockingQueue<String> queue;
    private final LoggerThread logger;

    private boolean isShutDown;
    private int reservations;

    public LogWriter(PrintWriter writer) {
        this.queue = new LinkedBlockingQueue<>();
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        // 添加关闭钩子
        Runtime.getRuntime().addShutdownHook(new Thread(LogWriter.this::stop));
        logger.start();
    }

    public void stop() {
        synchronized (this) {
            isShutDown = true;
        }
        logger.interrupt();
        // 展示结束
        System.out.println("end");
    }

    public void log(String msg) throws InterruptedException {

        synchronized (this) {
            if (isShutDown)
                throw new IllegalStateException("。。");
            // 维持一个消息提交计数器
            // 提交一次消息，加一
            ++reservations;
        }
        queue.put(msg);
    }

    private class LoggerThread extends Thread {

        private final PrintWriter writer;

        LoggerThread(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void run() {

            try {
                while (true) {
                    synchronized (LogWriter.this) {
                        // 当停下状态时，并且消息计数器为0时，才停止消费线程
                        if (isShutDown && reservations == 0)
                            break;
                    }
                    String msg = queue.take();
                    synchronized (LogWriter.this) {
                        // 消费一次，减一
                        --reservations;
                    }
                    writer.println(msg);
                }

            } catch (InterruptedException ignored) {
            } finally {
                writer.close();
            }
        }
    }
}
