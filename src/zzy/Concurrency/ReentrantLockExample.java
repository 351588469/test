package zzy.Concurrency;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
	int a=0;
	/**
	 * ReentranLock实现依赖于Java同步器框架AbstractQueuedSynchronizer.
	 * AQS使用一个整型的volatile变量（命名为state）来维护同步状态
	 */ 
	ReentrantLock lock=new ReentrantLock();
	public void writer(){
		lock.lock();
		try{
			a++;
		}finally{
			lock.unlock();
		}
	}
	
	public void reader(){
		lock.lock();
		try{
			int i=a;
		}finally{
			lock.unlock();
		}
	}
}
