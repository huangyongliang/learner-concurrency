package com.hyl.learnerconcurrency.practice;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: PrimeProducer.java, v 0.1 2020/10/14 8:57 $
 */
public class PrimeProducer extends Thread{

    private final BlockingQueue<BigInteger> queue;

    public PrimeProducer(BlockingQueue<BigInteger> queue){
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()){
                queue.put(p=p.nextProbablePrime());
            }
        }catch (InterruptedException e){
             e.printStackTrace();
        }
    }

    public void cancel(){
        interrupt();
    }
}
