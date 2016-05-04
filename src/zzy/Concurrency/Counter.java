package zzy.Concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
public class Counter {
	private AtomicInteger atomicI=new AtomicInteger(0);
	private int i=0;
	public static void main(String[] args){
		final Counter cas=new Counter();
		List<Thread>ts=new ArrayList<Thread>(600);
		long start=System.currentTimeMillis();
		for(int j=0;j<100;j++){
			Thread t=new Thread(new Runnable() {
				@Override
				public void run() {
					for(int i=0;i<10000;i++){
						cas.count();
						cas.safeCount();
					}
				}
			});
			ts.add(t);
		}
		for(Thread t:ts){
			t.start();
		}
		for(Thread t:ts){
			try{
				t.join();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		System.out.println(cas.i);
		System.out.println(cas.atomicI.get());
		System.out.println(System.currentTimeMillis()-start);
	}
	/**
	 * JVM中的CAS操作正式利用了处理器提供的CMPXCHG指令实现的。自旋CAS实现的基本思路就是循环进行CAS操作直到成功为止。
	 */
	private void safeCount(){//使用CAS实现线程安全计数器
		for(;;){
			int i=atomicI.get();
			//如果当前值等于预期值，则以原子方式将当前值设置为给定的更新值
			//该函数只有两个参数，可操作的为三个值，value,expect,update,使用由硬件保证原子性的指令CAS
			boolean suc=atomicI.compareAndSet(i,++i);//expect预期值,update新值
			//如果失败指示实际值与预期值不相等。
			if(suc){
				break;
			}
		}
	}
	private void count(){
		i++;
	}
	/**
	 * CAS实现原子操作的三大问题：
	 * ABA问题，循环时间长开销大，只能保证一个共享变量的原子操作
	 */
}
