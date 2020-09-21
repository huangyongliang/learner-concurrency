package com.hyl.learnerconcurrency.jmm;

/**
 * @author hyl
 * @version v1.0: Singleton.java, v 0.1 2020/9/22 1:40 $
 */
public class Singleton {

    private static class InstanceHolder{
        public static Singleton instance  = new Singleton();

        static {
            System.out.println("InstanceHolder init");
        }

    }

    public static Singleton getInstance(){
        return InstanceHolder.instance;
    }

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstance();

    }

}
