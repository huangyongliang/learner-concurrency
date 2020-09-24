package com.hyl.learnerconcurrency.threads.example;

/**
 * @author hyl
 * @version v1.0: SimpleHttpServerTest.java, v 0.1 2020/9/24 20:30 $
 */
public class SimpleHttpServerTest {
    public static void main(String[] args) throws Exception {
        String basePath = "D:\\git\\mybase\\projects\\github\\learner-concurrency\\src\\main\\java\\com\\hyl\\learnerconcurrency\\threads\\example\\path";
        SimpleHttpServer.setBasePath(basePath);
        SimpleHttpServer.setPort(8881);
        SimpleHttpServer.start();
    }
}
