package SynchronizTest;

import java.util.concurrent.TimeUnit;

public class NumberMachine implements Runnable {

	public static int NUM = 50;

	private static int index = 0;

	private static Object lock = new Object();

	@Override
	public void run() {
		while (index <= NUM) {
			synchronized (lock) {
				if(index<=NUM){
					System.out.println(Thread.currentThread().getName() + ":" + index++);
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(new NumberMachine());
		Thread thread2 = new Thread(new NumberMachine());
		Thread thread3 = new Thread(new NumberMachine());
		Thread thread4 = new Thread(new NumberMachine());
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
	}
}
