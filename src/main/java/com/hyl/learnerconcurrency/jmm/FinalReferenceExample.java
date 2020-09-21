package com.hyl.learnerconcurrency.jmm;

/**
 * @author hyl
 * @version v1.0: FinalRefenceExample.java, v 0.1 2020/9/22 0:00 $
 */
public class FinalReferenceExample {

    final int i;
    static FinalReferenceExample obj;

    public FinalReferenceExample() {
        i = 1;
        obj = this;
    }

    public static void writer(){
        new FinalReferenceExample();
    }

    public static void reader(){
        if (obj!=null){
            int temp = obj.i;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread A = new Thread(FinalReferenceExample::writer);
        Thread B = new Thread(FinalReferenceExample::reader);

        A.start();
        B.start();

        A.join();
        B.join();

    }
}
