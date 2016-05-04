package zzy.Concurrency.Initialization;
//非线程安全的延迟初始化对象
public class UnsafeLazyInitialization {
	private static Integer instance;
	public static Integer getInstance() throws Exception{
		if (instance == null) //1：A线程执行
			instance =Integer.class.newInstance(); //2：B线程执行
		return instance;
	}
}