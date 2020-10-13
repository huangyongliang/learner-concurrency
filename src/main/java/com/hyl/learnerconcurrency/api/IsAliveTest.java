package com.hyl.learnerconcurrency.api;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: IsAliveTest.java, v 0.1 2020/10/8 17:08 $
 */
public class IsAliveTest {

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = ()->{
            for (int i = 0; i < 30000; i++) {
            }
            try {
                Thread.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread runner = new Thread(runnable,"runner-thread");
        Thread watch = new Thread(new PrintStateRunner(runner),"watch-thread");
        watch.setDaemon(true);

        runner.start();
        watch.start();

    }

    static class PrintStateRunner implements Runnable{

        private Thread watch;

        public PrintStateRunner(Thread watch){
            this.watch = watch;
        }

        @Override
        public void run() {

            for (;;){
                if (watch.isAlive()){
                    System.out.println(Thread.currentThread().getName()+": "+watch.getState());
                }
            }

        }
    }

}
