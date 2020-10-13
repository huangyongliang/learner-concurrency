package com.hyl.learnerconcurrency.practice.cache;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: Computable.java, v 0.1 2020/10/12 23:24 $
 */
public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
