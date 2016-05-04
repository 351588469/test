package zzy.Concurrency.Thread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
/**
 * [4] Signal Dispatcher //分发处理发送给JVM信号的线程
 * [3] Finalizer //调用对象finalize方法的线程
 * [2] Reference Handler //清除Reference的线程
 * [1] main //main线程，用户程序入口
 */
/**
 * 使用多线程的主要原因：更多的处理器核心；更快的响应时间；更好的编程模型
 */
public class a_MultThread {
	public static void main(String[] args) {
		//获取Java线程管理MXBean
		ThreadMXBean threadMXBean=ManagementFactory.getThreadMXBean();
		//不需要获取同步的monitor和synchronizer信息,仅获取线程和线程堆栈信息
		ThreadInfo[]threadInfos=threadMXBean.dumpAllThreads(false,false);
		//遍历线程信息，仅打印线程ID和线程名称信息
		for(ThreadInfo threadInfo:threadInfos){
			System.out.println("["+threadInfo.getThreadId()+"] "+threadInfo.getThreadName());
		}
	}
}
