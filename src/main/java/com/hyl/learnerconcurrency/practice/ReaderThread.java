package com.hyl.learnerconcurrency.practice;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * <p>
 *
 * @author hyl
 * @version v1.0: ReaderThread.java, v 0.1 2020/10/14 12:35 $
 */
public class ReaderThread extends Thread{

    private final Socket socket;
    private final InputStream in;
    private final int BUFSZ = 1024;

    public ReaderThread(Socket socket) throws IOException{
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    @Override
    public void interrupt() {
        try {
            socket.close();
        }catch (IOException ignored){}
        finally {
            super.interrupt();
        }
    }

    @Override
    public void run() {
        try {
            byte[] buf = new byte[BUFSZ];
            while (true){
                int  count = in.read(buf);
                if (count<0)
                    break;
                else if (count>0)
                    processBuffer(buf,count);
            }
        }catch (IOException e){
            // 允许线程退出
        }
    }

    private void processBuffer(byte[] buf,int count){}

}
