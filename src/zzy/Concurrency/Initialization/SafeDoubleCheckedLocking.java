package zzy.Concurrency.Initialization;
/**
 *禁止初始化对象和设置instance指向内存空间的重排序来保证线程安全的延迟初始化。
 *
 */
public class SafeDoubleCheckedLocking {
    private volatile static Integer instance;
    public static Integer getInstance() throws InstantiationException, IllegalAccessException {
        if (instance == null) {
            synchronized (SafeDoubleCheckedLocking.class) {
                if (instance == null)
                    instance =Integer.class.newInstance();//instance为volatile，现在没问题了
            }
        }
        return instance;
    }
}