package zzy.Concurrency.Initialization;
/**
 *JVM在类的初始化阶段（即在Class被加载后，且被线程使用之前），会执行类的初始化。
 *在执行类的初始化期间，JVM会去获取一个锁。这个锁可以同步多个线程对同一个类的初始化。
 *基于这个特性，可以实现另一种线程安全的延迟初始化方案（这个方案被称之为Initialization On Demand Holder idiom）
 */
/**
 *允许初始化对象和设置instance指向内存空间的重排序，但不允许非构造线程“看到”这个重排序
 *
 */
/**
 * 初始化一个类，包括执行这个类的静态初始化和初始化在这个类中声明的静态字段。
 * 根据Java语言规范，在首次发生下列任意一种情况时，一个类或接口类型T将立即初始化。
 * 1）T是一个类，而且一个T类型的实例被创建。
 * 2）T是一个类，且T中声明的一个静态方法被调用。
 * 3）T中声明的一个静态字段被赋值。
 * 4）T中声明的一个静态字段被使用，而且这个字段不是一个常量字段。
 * 5）T是一个顶级类（Top Level Class），而且一个断言语句嵌套在T内部被执行。
 * 
 * 类初始化过程（condition和state标记为虚构的，JVM具体实现只要实现类似功能即可）
 * 1）通过在Class对象上同步（获取Class对象的初始化锁），来控制类或接口的初始化。这个获取锁的线程会一直等待，直到当前线程能够获取到这个初始化锁。
 * 		A1:尝试获取Class对象的初始化锁。这里假设线程A获取到了初始化锁。
 * 		B1:尝试获取Class对象的初始化锁，由于线程A获取到了锁，线程B将一直等待获取初始化锁。
 * 		A2:线程A看到线程还未被初始化（因为读取到state==noInitialzation）,线程设置state==initialzing.
 * 		A3:线程A释放初始化锁
 * 2）线程A执行类的初始化，同时线程B在初始化锁对应的condition上等待。
 * 		A1:执行类的静态初始化和初始化类中声明的静态字段。
 * 		B1:获取到初始化锁。
 * 		B2:读取到state==initialzing
 * 		B3:释放初始化锁
 * 		B4:在初始化锁的condition中等待
 * 3）线程A设置state==initialized,然后唤醒在condition中的所有线程
 * 		A1:获取初始化锁
 * 		A2:设置state==initialized
 * 		A3:唤醒在condition中等待的所有线程。
 * 		A4:释放初始化锁。
 * 		A5:线程A的初始化处理过程完成。
 * 4）线程B结束类的初始化处理。
 * 		B1:获取初始化锁。
 * 		B2:读取到state=initialized
 * 		B3:释放初始化锁
 *      B4:线程B的类初始化处理过程完成
 */
/**
 * 	对比基于volatile的双重检查锁定的方案和基于类初始化的方案，我们会发现基于类初始话的方案的实现代码更简介。
 *但基于volatile的双重检查锁定的方案有一个额外的优势:除了可以对静态字段实现延迟初始化外，还可以对实例字段实现延迟初始化。
 *	字段延迟初始化降低了初始化类或创建实例的开销，但增加了访问被延迟初始化的字段的开销。在大多数时候，正常的初始化要优于延迟初始化。
 *如果确实需要对实例字段使用线程安全的延迟初始化，使用基于volatile的延迟初始化的方案；
 *如果确实需要对静态字段使用线程安全的延迟初始化，使用基于类初始化的方案。  
 */
public class InstanceFactory {
    private static class InstanceHolder {
        public static Integer instance;
        //public static Instance instance = new Instance();
    }
    public static Integer getInstance() {
        return InstanceHolder.instance ;  //这里将导致InstanceHolder类被初始化，符合情况4
    }
}
