package com.hyl.learnerconcurrency.pool;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: RunnableResult.java, v 0.1 2020/10/8 14:02 $
 */
public class RunnableResult {

    public static void main(String[] args) throws InterruptedException {

        ResultRun resultRun = new ResultRun();

        Thread thread = new Thread(resultRun, "result-thread");

        thread.start();
        long start = resultRun.get();
        System.out.println(System.nanoTime()-start);

    }

    static class ResultRun implements Runnable {

        private long result;

        private volatile boolean flag = false;

        @Override
        public void run() {

            result = System.nanoTime();
            flag = true;

        }

        public long get() {
            for (;;){
                if (flag){
                    return result;
                }
            }
        }
    }

}
