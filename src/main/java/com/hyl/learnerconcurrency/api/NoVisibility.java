package com.hyl.learnerconcurrency.api;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: NoVisibility.java, v 0.1 2020/10/10 16:57 $
 */
public class NoVisibility {
    static volatile boolean ready=false;
    static int number;



    public static void main(String[] args) throws InterruptedException {


          Runnable runnable = ()->{
              while (!ready){
                  // Thread.yield();
              }
              System.out.println(number);
          };

          Thread[] threads = new Thread[1000];
        for (int i = 0; i < 1000; i++) {
            threads[i] =  new Thread(runnable,"thread - "+i);
            threads[i].start();
        }

        number = 46;
        ready = true;

        for (int i = 0; i < 1000; i++) {
            threads[i].join();
        }
    }


}
