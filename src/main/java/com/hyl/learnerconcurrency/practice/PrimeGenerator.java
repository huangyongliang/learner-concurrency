package com.hyl.learnerconcurrency.practice;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 素数
 * <p>
 *
 * @author hyl
 * @version v1.0: PrimeGenerator.java, v 0.1 2020/10/13 23:53 $
 */
public class PrimeGenerator implements Runnable {

    private final List<BigInteger> primes = new ArrayList<>();

    private volatile boolean cancelled;

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;

        while (!cancelled){
            p = p.nextProbablePrime();
            synchronized (this){
                primes.add(p);
            }
        }
    }

    public void cancel(){
        cancelled = true;
    }

    public synchronized List<BigInteger> get(){
        return new ArrayList<>(primes);
    }


}
