package com.hyl.learnerconcurrency.jmm;

/**
 * final测试
 *
 * @author hyl
 * @version v1.0: FinalExample.java, v 0.1 2020/9/21 23:03 $
 */
public class FinalExample {

     int i ;
    final  int j ;

    static FinalExample obj;

    public FinalExample(){
        i =2;
        j = 1;
    }


    public static void writer(){
        obj = new FinalExample();
    }

    public static void reader(){
        FinalExample object = obj;
        if (obj==null)
            return;
        int a = object.i;
        int b = object.j;

        System.out.println("a = "+a+",b = "+b);
    }

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 1000; i++) {
            Thread thread1 = new Thread(FinalExample::writer);
            Thread thread2 = new Thread(FinalExample::reader);
            thread1.start();
            thread2.start();

            thread1.join();
            thread2.join();

            obj =null;

            System.out.println(i);
        }

    }

}
