package com.hyl.learnerconcurrency.common;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author hyl
 * @version v1.0: ThreadUtils.java, v 0.1 2020/9/23 2:00 $
 */
public abstract class ThreadUtils {

    /**
     * 打印 Java 启动时的所有线程信息
     */
    public static void printAllThreadState(){
        // 获取Java线程管理MXBean
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        // 不需要获取同步的monitor 和 synchronizer 信息，仅获取线程和线程堆栈信息
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        // 遍历线程信息，打印线程id、线程名称、线程状态
        for (ThreadInfo threadInfo : threadInfos) {
            System.out.println(
                "[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName() + "," + threadInfo.getThreadState());
        }
    }

}
