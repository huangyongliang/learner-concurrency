package com.hyl.learnerconcurrency.practice;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: SocketUsingTask.java, v 0.1 2020/10/14 13:02 $
 */
public abstract class SocketUsingTask<T> implements CancellableTask<T> {

    private Socket socket;

    protected synchronized void setSocket(Socket s){socket = s;}

    public synchronized void cancel(){
        try {
            if (socket!=null)
                socket.close();
        }catch (IOException ignored){

        }
    }

    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this){
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    SocketUsingTask.this.cancel();
                }finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }
}
