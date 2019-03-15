public class FakeLock {

	/**
	 * 加锁本质就是在锁对象的对象头中写入当前线程id，但是new object每次在内存中都是新对象，所以加锁无效。
	 * 			sync锁的对象monitor指针指向一个ObjectMonitor对象，所有线程加入他的entrylist里面，去cas抢锁，更改state加1拿锁，执行完代码，释放锁state减1，和aqs机制差不多，只是所有线程不阻塞，cas抢锁，没有队列，属于非公平锁。
	 * wait的时候，线程进waitset休眠，等待notify唤醒
	 * 			两把不同的锁，不能保护临界资源。而且这种new出来只在一个地方使用的对象，其它线程不能对它解锁，这个锁会被编译器优化掉。和没有syncronized代码块效果是相同的
	 */

	//问题：锁的实现
	long value = 0L;

	long get() {
		synchronized (new Object()) {
			return value;
		}
	}

	void addOne() {
		synchronized (new Object()) {
			value += 1;
		}
	}

}
