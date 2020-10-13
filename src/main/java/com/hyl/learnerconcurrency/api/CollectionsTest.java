package com.hyl.learnerconcurrency.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 将一个map转为线程安全的类
 * <p>
 *
 * @author hyl
 * @version v1.0: CollectionsTest.java, v 0.1 2020/10/11 17:52 $
 */
public class CollectionsTest {

    public static void main(String[] args) {

        HashMap<String,String>map = new HashMap<>();

        // 新建一个装饰对象，将每个接口都定义成 synchronized 的同步代码块
        Map<String, String> stringStringMap = Collections.synchronizedMap(map);

        Vector<String> vector = new Vector<>(10);


    }

}
