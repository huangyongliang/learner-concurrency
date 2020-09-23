package com.hyl.learnerconcurrency.threads.example;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * @author hyl
 * @version v1.0: ConnectionDriver.java, v 0.1 2020/9/24 2:31 $
 */
public class ConnectionDriver {

    static class ConnectionHandler implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName()
                .equals("commit")) {
                TimeUnit.MILLISECONDS.sleep(100);
            }
            return null;
        }
    }

    /**
     * 创建代理类
     * @return
     */
    public  static final Connection createConnection() {
        return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
            new Class<?>[] {Connection.class}, new ConnectionHandler());
    }

}
