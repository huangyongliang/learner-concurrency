package com.hyl.learnerconcurrency.practice.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: Memorizer3.java, v 0.1 2020/10/12 23:42 $
 */
public class Memorizer3 <A,V> implements Computable<A,V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A,V> c;


    public Memorizer3(Computable<A,V> c){
        this.c = c;
    }
    @Override
    public V compute(A arg) throws InterruptedException {

        Future<V> f = cache.get(arg);
        if (f==null){
            FutureTask<V> ft = new FutureTask<>(() -> c.compute(arg));
            f = ft;
            cache.put(arg,ft);
            ft.run();
        }
        try {
            return f.get();
        }catch (ExecutionException e){
            throw  new InterruptedException();
        }
    }
}
