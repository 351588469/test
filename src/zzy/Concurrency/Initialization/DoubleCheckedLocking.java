package zzy.Concurrency.Initialization;
//错误的优化
//双重检查锁定
public class DoubleCheckedLocking {                 //1
    private static Integer instance;                    //2
    public static Integer getInstance() throws InstantiationException, IllegalAccessException {               //3
        if (instance == null) {                          //4:第一次检查
            synchronized (DoubleCheckedLocking.class) {  //5:加锁
                if (instance == null)                    //6:第二次检查
                    instance =Integer.class.newInstance();           //7:问题的根源出在这里
                /**
                 * memory=allocate();//1 分配对象的内存空间
                 * instance=memory;//2 设置instance指向刚分配的内存地址
                 * ctorInstance(memory);//3 初始化对象;
                 * 所有线程在执行Java程序时必须要遵守intra-thread semantic,保证重排序不会改变单线程内的程序执行结果。
                 * 即，2和3可以发生重排序。
                 * 从而，如果发生重排序，另一个并发执行的线程B就有可能在第4行判断instance不为null.
                 * 	线程B接下来将访问instance所引用的对象，但此时这个对象可能还没有被线程A初始化。
                 */
                
            }                                            //8
        }                                                //9
        return instance;                                 //10
    }                                                    //11
}                                                        //12