package com.hyl.learnerconcurrency.practice.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.hyl.learnerconcurrency.practice.BoundedBuffer;

import junit.framework.TestCase;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: BoundedBufferTest.java, v 0.1 2020/10/16 11:22 $
 */
public class BoundedBufferTest extends TestCase {


    private static final int LOCKUP__DETECT_TIMOUT = 2 * 1000;

    public void testIsEmptyWhenConstructed(){
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    public void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        for (int i = 0; i < 10; i++) {
            bb.put(i);
        }
        assertTrue(bb.isFull());
        assertFalse(bb.isEmpty());
    }

    /**
     * 测试阻塞行为以及对中断的响应性
     */
    public void testTakeBlocksWhenEmpty(){
        final BoundedBuffer<Integer> bb = new BoundedBuffer<>(10);
        Thread take = new Thread(() -> {
            try {
                int unused = bb.take();
                // 如果执行到这里，那么表示出现了一个错误
                fail();
            } catch (InterruptedException success) {
                System.out.println("中断了");
            }
        });
        try {
            take.start();
            Thread.sleep(LOCKUP__DETECT_TIMOUT);
            take.interrupt();
            take.join(LOCKUP__DETECT_TIMOUT);
            assertFalse(take.isAlive());
        }catch (Exception e){
            fail();
        }

    }

    public void testPoolExpansion() throws InterruptedException {
        int MAX_SIZE = 10;
        TestingThreadFactory threadFactory = new TestingThreadFactory();
        ExecutorService exec = Executors.newFixedThreadPool(MAX_SIZE,threadFactory);

        for (int i = 0; i < 10 * MAX_SIZE; i++) {
            exec.execute(()->{
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        for (int i = 0; i < 20 && threadFactory.numCreated.get() <MAX_SIZE; i++) {
            Thread.sleep(100);
        }

        assertEquals(threadFactory.numCreated.get(),MAX_SIZE);
        exec.shutdown();
    }



    static class TestingThreadFactory implements ThreadFactory{

        public final AtomicInteger numCreated = new AtomicInteger();
        private final ThreadFactory factory = Executors.defaultThreadFactory();

        @Override
        public Thread newThread(Runnable r) {
            numCreated.incrementAndGet();
            return factory.newThread(r);
        }
    }

}
