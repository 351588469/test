package zzy.Concurrency;
/**
 * 当第二个操作是volatile写时，不管第一个操作是什么，都不能重排序。确保volatile写之前的操作不会被编译器重排序到volatile之后。
 * 当第一个操作是volatile读时，不管第二个操作是什么，都不能重排序。
 * 当第一个操作是volatile写，第二个操作是volatile读时，不能重排序。
 * 基于保守策略的JMM内存屏障插入策略。
 * 	在每个volatile写的操作前插入StoreStore屏障。
 * 	在每个volatile写操作的后面插入StoreLoad屏障。
 *	在每个volatile读操作的后面插入LoadLoad屏障。
 *	在每个volatile读操作的后面插入LoadStore屏障。
 */
public class Volatile {
	volatile long v1=0l;
}
/**
 * final域的重排序规则
 * 1）在构造函数内对一个final域的写入，与随后把这个被构造对象的引用复制给一个引用变量，这两个操作之间不能重排序。
 * 2）初次读一个包含final域的对象的引用，与随后除此读这个final域，这两个操作之间不能重排序。
 */
