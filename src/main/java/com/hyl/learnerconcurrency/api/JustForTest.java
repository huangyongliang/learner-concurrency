package com.hyl.learnerconcurrency.api;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: JustForTest.java, v 0.1 2020/10/12 10:13 $
 */
public class JustForTest {

    public static void main(String[] args) {

        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<>();


        for (Map.Entry entry : map.entrySet()){
            System.out.println(entry.getValue());
        }
        map.toString();

        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(10000));
        }
    }

}
