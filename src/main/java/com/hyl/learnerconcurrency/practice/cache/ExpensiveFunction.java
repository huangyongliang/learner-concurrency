package com.hyl.learnerconcurrency.practice.cache;

import java.math.BigInteger;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: ExpensiveFunction.java, v 0.1 2020/10/12 23:25 $
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        // 在经过长时间的计算后
        return new BigInteger(arg);
    }
}
