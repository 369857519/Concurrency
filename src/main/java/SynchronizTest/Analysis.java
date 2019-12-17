package SynchronizTest;

public class Analysis {

	private static Integer index = 0;

	public static void test() {
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static synchronized void staticTest()  {
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void blockTest() {
		synchronized (this) {
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void blockStaticTest() {
		synchronized (Analysis.class) {
			try {
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		Thread testThread = new Thread(Analysis::test);
		testThread.setName("testThread");
		Thread staticTestThread = new Thread(Analysis::staticTest);
		staticTestThread.setName("staticTestThread");
		Thread blockTest = new Thread(() -> {
			new Analysis().blockTest();
		});
		blockTest.setName("blockTest");
		Thread blockStaticTest = new Thread(Analysis::blockStaticTest);
		blockStaticTest.setName("blockStaticTest");
		testThread.start();
		staticTestThread.start();
		blockTest.start();
		blockStaticTest.start();
	}
}
