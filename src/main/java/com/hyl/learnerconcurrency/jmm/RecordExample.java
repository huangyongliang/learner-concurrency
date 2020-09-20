package com.hyl.learnerconcurrency.jmm;

/**
 * @author hyl
 * @version v1.0: RecordExample.java, v 0.1 2020/9/21 2:18 $
 */
public class RecordExample {

    int a = 0;
    volatile boolean flag = false;

    int i = 0;

    public void writer() {
        a = 1;
        flag = true;
    }

    public void reader() {
        if (flag) {
            i = a * a;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RecordExample recordExample = new RecordExample();

        Thread thread1 = new Thread(recordExample::writer);
        Thread thread2 = new Thread(recordExample::reader);

        thread2.start();
        thread1.start();


        thread1.join();
        thread2.join();

        System.out.println("a = " + recordExample.a + " , flag = " + recordExample.flag + ", i = " + recordExample.i);
    }

}
