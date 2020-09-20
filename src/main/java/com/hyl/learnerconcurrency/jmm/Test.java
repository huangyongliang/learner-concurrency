package com.hyl.learnerconcurrency.jmm;

/**
 * @author hyl
 * @version v1.0: Test.java, v 0.1 2020/9/21 3:47 $
 */
public class Test {
    private volatile int a = 1;

    private int b =1;

    public void writer(){
        b = 2;
        a= 2;
    }
}
