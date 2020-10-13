package com.hyl.learnerconcurrency.practice.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: Memorizer1.java, v 0.1 2020/10/12 23:30 $
 */
public class Memorizer1<A,V> implements Computable<A,V>{


    private final Map<A,V> cache = new HashMap<>();
    private final Computable<A,V> c;


    public Memorizer1(Computable<A,V> c){
        this.c = c;
    }

    @Override
    public synchronized V compute(A arg) throws InterruptedException {

        V result = cache.get(arg);
        if (result == null){
            result = c.compute(arg);
            cache.put(arg,result);
        }
        return result;
    }
}
