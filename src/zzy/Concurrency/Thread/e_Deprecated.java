package zzy.Concurrency.Thread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import zzy.Concurrency.util.SleepUtils;
/**
 *	不建议使用的原因主要有：以suspend()方法为例,在调用后,线程不会释放已经占有的资源(比如锁),而是占有着资源进入睡眠状态,
 *这样容易引发死锁问题。同样,stop()方法在终结一个线程时不会保证线程的资源正常释放,通常是没有给予线程完成资源释放工作的机会,
 *因此会导致程序可能工作在不确定状态下.
 *	正因为suspend(),resume()和stop()方法带来的副作用,这些方法才被标注为不建议使用的过期方法,而暂停和恢复操作可以用
 *后来提到的等待/通知机制来替代.
 */
public class e_Deprecated {
	@SuppressWarnings("deprecation")
	public static void main(String[] args)throws Exception{
		DateFormat format=new SimpleDateFormat("HH:mm:ss");
		Thread printThread=new Thread(new Runner(),"PrintThread");
		printThread.setDaemon(true);
		printThread.start();
		TimeUnit.SECONDS.sleep(3);
		//将PrintThread进行暂停，输出内容工作停止
		printThread.suspend();
		System.out.println("main suspend PrintThread at "+format.format(new Date()));
		TimeUnit.SECONDS.sleep(3);
		//将PrintThread进行恢复，输出内容继续
		printThread.resume();
		System.out.println("main resume PrintThread at "+format.format(new Date()));
		TimeUnit.SECONDS.sleep(3);
		//将PrintThread进行终止，输出内容停止
		printThread.stop();
		System.out.println("main stop PrintThread at "+format.format(new Date()));
		TimeUnit.SECONDS.sleep(3);
	}
	static class Runner implements Runnable{
		@Override
		public void run() {
			DateFormat format=new SimpleDateFormat("HH:mm:ss");
			while(true){
				System.out.println(Thread.currentThread().getName()+" Run at "+format.format(new Date()));
				SleepUtils.second(1);
			}
		}
	}
}
