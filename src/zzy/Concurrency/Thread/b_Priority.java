package zzy.Concurrency.Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
/**
 * 	在Java线程中，通过一个整型成员变量priority来控制优先级，优先级的范围从1-10,在线程构建的时候可以通过setPriority(int)方法
 *来修改优先级，默认优先级是5，优先级高的线程分配时间片的数量要多于优先级低的线程。设置线程优先级时，针对频繁阻塞（休眠或者I/O操作）的线程
 *则设置较低的优先级，确保处理器不会被独占。在不同的JVM及操作系统上，线程规划会存在差异，有些操作系统甚至会忽略对线程优先级的设定。
 */
/**
 *	从输出可以看到线程优先级没有生效，这表示程序正确性不能依赖线程的优先级高低。
 */
public class b_Priority {
	private static volatile boolean notStart=true;
	private static volatile boolean notEnd=true;
	public static void main(String[] args) throws Exception{
		List<Job>jobs=new ArrayList<Job>();
		for(int i=0;i<10;i++){
			int priority=i<5?Thread.MIN_PRIORITY:Thread.MAX_PRIORITY;
			Job job=new Job(priority);
			jobs.add(job);
			Thread thread=new Thread(job,"Threaed:"+i);
			thread.setPriority(priority);
			thread.start();
		}
		notStart=false;
		TimeUnit.SECONDS.sleep(10);
		notEnd=false;
		for(Job job:jobs){
			System.out.println("Job Priority: "+job.priority+", Count : "+job.jobCount);
		}
	}
	static class Job implements  Runnable{
		private int priority;
		private long jobCount;
		public Job(int priority){
			this.priority=priority;
		}
		public void run(){
			while (notStart) {
				Thread.yield();
			}
			while (notEnd) {
				Thread.yield();
				jobCount++;
			}
		}
	}
}
