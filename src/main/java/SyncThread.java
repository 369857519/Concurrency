import java.text.SimpleDateFormat;
import java.util.Date;

//同一对象上的锁
class SyncThread implements Runnable {

	public static void main(String[] args) {
		//Synchronized有两种方式
		//https://juejin.im/post/594a24defe88c2006aa01f1c
		/*
			获取对象锁 //仅在本对象内起作用
				synchronized(this|object) {}
				修饰非静态方法
			获取类锁  //全局作用
				synchronized(类.class) {}
				修饰静态方法
		 */
		SyncThread syncThread = new SyncThread();
//		Thread A_thread1 = new Thread(syncThread, "A_thread1");
//		Thread A_thread2 = new Thread(syncThread, "A_thread2");
//		Thread B_thread1 = new Thread(syncThread, "B_thread1");
//		Thread B_thread2 = new Thread(syncThread, "B_thread2");
		Thread C_thread1 = new Thread(syncThread, "C_thread1");
		Thread C_thread2 = new Thread(syncThread, "C_thread2");
		//A中第一个后后一个的打点都是乱序的
//		A_thread1.start();
//		A_thread2.start();
		//sync以后的代码会仅给某一个线程访问，sync之前的代码仍然是乱序的
//		B_thread1.start();
//		B_thread2.start();
		//方法内的代码编程了完全顺序执行
		C_thread1.start();
		C_thread2.start();
	}

	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		if (threadName.startsWith("A")) {
			async();
		} else if (threadName.startsWith("B")) {
			sync1();
		} else if (threadName.startsWith("C")) {
			sync2();
		}

	}

	/**
	 * 异步方法
	 */
	private void async() {
		try {
			System.out.println(Thread.currentThread().getName() + "_Async_Start: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getName() + "_Async_End: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 方法中有 synchronized(this|object) {} 同步代码块
	 */
	private void sync1() {
		System.out.println(Thread.currentThread().getName() + "_Sync1: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
		synchronized (SyncThread.class) {
			try {
				System.out.println(Thread.currentThread().getName() + "_Sync1_Start: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
				Thread.sleep(2000);
				System.out.println(Thread.currentThread().getName() + "_Sync1_End: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * synchronized 修饰非静态方法
	 */
	private synchronized static void sync2() {
		System.out.println(Thread.currentThread().getName() + "_Sync2: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
		try {
			System.out.println(Thread.currentThread().getName() + "_Sync2_Start: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getName() + "_Sync2_End: " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

