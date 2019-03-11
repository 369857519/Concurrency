import java.util.concurrent.atomic.AtomicInteger;

// 以下代码来源于【参考 1】
//vilatile 易变的，反复无常的  c语言中用作禁用CPU缓存的意思
/* 1、单线程顺序规则
 * 2、volatle变量规则：Happens-Before 对这个变量的写操作Happens-Before之后的对变量的读操作
 * 3、Happens-Before 有传递性
 * 4、管程中锁的规则。管程是一种同步的术语，Java中值得是synchronized，synchronized就是java中对管程的实现
 *  synchronized在大括号开始阶段自动加锁，在括号结尾自动解锁。解锁Happends-Before后面一次的加锁
 * 5、线程start规则 子线程中能看到主线程启动之前的操作
 * 6、线程join规则 子线程中的任何操作Happens-Before该线程的join操作
 *
 * final与逸出。final修饰变量表示变量不可变，但是有些情况下会有其它情况http://www.cs.umd.edu/~pugh/java/memoryModel/jsr-133-faq.html#finalWrong
 * 线程中断规则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生，可以通过Thread.interrupted()方法检测到是否有中断发生。
 * 对象终结规则：一个对象的初始化完成(构造函数执行结束)先行发生于它的finalize()方法的开始。
 *
 * 如何使一个变量在Happens-Before原则下其他线程可见:
 *  1.volatile
 *  2.sychonized内进行赋值
 *  3.join之后再起其他线程
 *  4.使用AtomicInteger类
 */
class VolatileExample {

	int x = 0;
	boolean v = false;

	public void writer() {
		x = 42;
		v = true;
	}

	public void reader() {
		if (v == true) {
			System.out.println(x);
			// 这里 x 会是多少呢？
			// 1.5前，有可能是0
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// 5、线程start规则 主线程
		VolatileExample volatileExample = new VolatileExample();
		Thread thread = new Thread(() -> {
			System.out.println(volatileExample.x);
			volatileExample.x = 10;
		});
		System.out.println(thread.isInterrupted());
		volatileExample.x = 1;
		thread.start();
		//这一条并不能保证先后顺序，1、主线程执行时间，2、cpu被抢占 均会影响输出结果
		System.out.println(volatileExample.x);
		thread.join();
		//6、线程join规则
		System.out.println(volatileExample.x);

		//总的来说就是：start之前，主线程代码结果必然在子线程代码里可见，join之后，子线程执行结果必然在主线程中可见，中间的代码则有可能因为争抢cpu执行结果不同。
	}

	//原子同步类

	class Test1 {
		private volatile int count = 0;

		public synchronized void increment() {
			count++; //若要线程安全执行执行count++，需要加锁
		}

		public int getCount() {
			return count;
		}
	}

	class Test2 {
		private AtomicInteger count = new AtomicInteger();

		public void increment() {
			count.incrementAndGet();
		}
		//使用AtomicInteger之后，不需要加锁，也可以实现线程安全。
		public int getCount() {
			return count.get();
		}
	}
}
