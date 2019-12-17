package SynchronizTest;

import java.util.ArrayList;
import java.util.List;

public class NumberMachine implements Runnable {

	public static int NUM = 50;

	private static int index = 0;

	private static List<Integer> record = new ArrayList<>();

	@Override
	public void run() {
		synchronized (this) {
			while (index <= NUM) {
				record.add(index);
				System.out.println(Thread.currentThread().getName() + ":" + index++);
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
		thread1.join();
		thread2.join();
		thread3.join();
		thread4.join();
		for (int i = 0; i < record.size(); i++) {
			System.out.println("record finished" + record.get(i));
		}
	}
}
