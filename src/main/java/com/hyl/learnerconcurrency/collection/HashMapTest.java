package com.hyl.learnerconcurrency.collection;

import java.util.HashMap;

/**
 * @author hyl
 * @version v1.0: HashMapTest.java, v 0.1 2020/9/28 6:31 $
 */
public class HashMapTest {

    public static void main(String[] args) throws InterruptedException {
        HashMap<TestKey,Integer > map = new HashMap<>();
    }




    static class TestKey{

        final int hash;

        TestKey(int hash){
            this.hash = hash;
        }

        @Override
        public int hashCode() {
            return hash;
        }
    }
}
