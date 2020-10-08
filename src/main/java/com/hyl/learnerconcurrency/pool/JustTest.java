package com.hyl.learnerconcurrency.pool;

import java.util.concurrent.locks.LockSupport;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: JustTest.java, v 0.1 2020/10/6 10:07 $
 */
public class JustTest {

    public static void main(String[] args) {

          int COUNT_BITS = Integer.SIZE - 3;
          int CAPACITY   = (1 << COUNT_BITS) - 1;

        System.out.println(Integer.toBinaryString(CAPACITY));
        System.out.println(Integer.toBinaryString(~ CAPACITY));

        System.out.println(Runtime.getRuntime().availableProcessors());
        LockSupport.park();
    }

}
