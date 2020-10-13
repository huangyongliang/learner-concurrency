package com.hyl.learnerconcurrency.practice.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: Memorizer2.java, v 0.1 2020/10/12 23:40 $
 */
public class Memorizer2 <A,V> implements Computable<A,V>{

    private final Map<A,V> cache = new ConcurrentHashMap<>();
    private final Computable<A,V> c;


    public Memorizer2(Computable<A,V> c){
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {

        V result = cache.get(arg);
        if (result == null){
            result = c.compute(arg);
            cache.put(arg,result);
        }
        return result;
    }
}
