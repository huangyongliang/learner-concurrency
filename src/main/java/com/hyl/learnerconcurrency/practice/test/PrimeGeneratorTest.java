package com.hyl.learnerconcurrency.practice.test;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.hyl.learnerconcurrency.practice.PrimeGenerator;
import com.hyl.learnerconcurrency.practice.PrimeProducer;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: PrimeGeneratorTest.java, v 0.1 2020/10/13 23:57 $
 */
public class PrimeGeneratorTest {

    @Test
    public  void test1() {
        PrimeGenerator generator = new PrimeGenerator();

        new Thread(generator).start();

        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            generator.cancel();
        }

        System.out.println(generator.get());
    }

    @Test
    public void test2(){

        BlockingQueue<BigInteger> queue = new LinkedBlockingQueue<>();

        PrimeProducer generator = new PrimeProducer(queue);
        generator.start();

        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            generator.cancel();
        }

        System.out.println(queue.toString());
    }

}
