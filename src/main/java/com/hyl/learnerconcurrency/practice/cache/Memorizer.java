package com.hyl.learnerconcurrency.practice.cache;

import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: Memorizer.java, v 0.1 2020/10/12 23:53 $
 */
public class Memorizer<A, V> implements Computable<A, V> {

    private final Map<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> c;

    public Memorizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        // 为什么要循环
        // 如果第一次get，判断cache中没有，则 f 为null；
        // 第二次put，判断cache中已经存在了，则跳过run方法，同时需要循环返回get一下。
        for(;;){
            Future<V> f = cache.get(arg);
            if (f == null) {
                FutureTask<V> ft = new FutureTask<>(() -> c.compute(arg));
                // 若没有就添加
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg, f);
            } catch (ExecutionException e) {
                throw new InterruptedException();
            }
        }
    }
}
