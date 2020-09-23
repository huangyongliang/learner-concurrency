package com.hyl.learnerconcurrency.threads.example;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hyl
 * @version v1.0: ConnectionPoolTest.java, v 0.1 2020/9/24 2:49 $
 */
public class ConnectionPoolTest {

    static ConnectionPool pool = new ConnectionPool(10);

    // 保留所有 ConnectionRunner 能够同时开始
    static CountDownLatch start = new CountDownLatch(1);
    // main 线程将会等待所有 ConnectionRunner 结束后才能继续执行
    static CountDownLatch end;

    public static void main(String[] args) throws InterruptedException {
        // 线程数量，可以修改线程数量经行观察
        int threadCount = 18;
        end = new CountDownLatch(threadCount);

        int count = 20;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }

        start.countDown();
        end.await();
        System.out.println("total invoke: " + (threadCount * count));
        System.out.println("got connection: " + got);
        System.out.println("not got connection: " + notGot);
        System.out.println("not got / got : " + notGot.doubleValue() / got.doubleValue());
    }

    static class ConnectionRunner implements Runnable {

        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {

            try {
                start.await();
            } catch (Exception ex) {
            }

            while (count > 0) {
                try {
                    // 从连接池中获取连接，如果 1000 ms 内无法获取到，将会返回 null
                    // 分别统计连接获取的数量 got 和未获取到的连接的数量 notGot
                    Connection connection = pool.fetchConnection(2000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }

                } catch (Exception ex) {
                } finally {
                    count--;
                }
            }
            end.countDown();

        }
    }

}
