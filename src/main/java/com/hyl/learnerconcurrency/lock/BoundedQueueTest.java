package com.hyl.learnerconcurrency.lock;

import com.hyl.learnerconcurrency.common.SleepUtils;

/**
 * BoundedQueue测试类
 *
 * @author hyl
 * @version v1.0: BoundedQueueTest.java, v 0.1 2020/9/28 4:37 $
 * @see BoundedQueue
 */
public class BoundedQueueTest {

    static BoundedQueue<Integer> boundedQueue = new BoundedQueue<>(10);

    public static void main(String[] args) {

        Thread add = new Thread(new AddRunner(),"add-thread");
        Thread remove = new Thread(new RemoveRunner(),"del-thread");

        add.start();
        SleepUtils.second(6);
        remove.start();

    }


    static class AddRunner implements Runnable{
        @Override
        public void run() {
            for (;;){
                try {
                    Node node = getNode(29);
                    boundedQueue.add(node.value);
                    SleepUtils.second(node.sleep);
                    System.out.println(Thread.currentThread().getName()+": value->"+node.value+",sleep->"+node.sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class RemoveRunner implements Runnable{
        @Override
        public void run() {
            for (;;){
                try {
                    Node node = getNode(29);
                    int v =  boundedQueue.remove();
                    SleepUtils.second(node.sleep);
                    System.out.println(Thread.currentThread().getName()+": value->"+v+",sleep->"+node.sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static Node getNode(int i){
        Object obj = new Object();
        Node node = new Node();
        node.value = obj.hashCode();
        node.sleep = obj.hashCode()>>i;
        return node;
    }

    static class Node{
        int sleep;
        int value;
    }


}
