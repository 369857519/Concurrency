package Volatile;

import java.util.concurrent.TimeUnit;

//保证可见性和禁止重排序
public class VolatileTest {

	public static int index = 0;

	private static final int MAX = 5;

	public static void main(String[] args) {
		Thread readThread = new Thread(() -> {
			int currentIndex = 0;
			while (index <= MAX) {
				if (index - currentIndex == 1) {
					System.out.println(index);
					currentIndex++;
				}
			}
		});
		readThread.setName("readThread");

		Thread updateThread = new Thread(() -> {
			while (index <= MAX) {
				index++;
				try {
					System.out.println("update to:" + index);
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		updateThread.setName("updateThread");

		readThread.start();
		updateThread.start();
	}

}
