
public class SyncTest {

	public static void main(String... args) {
		//全部为乱序
//		Thread A_thread1 = new Thread(new SyncThread(), "A_thread1");
//		Thread A_thread2 = new Thread(new SyncThread(), "A_thread2");
		Thread B_thread1 = new Thread(new SyncThread(), "B_thread1");
		Thread B_thread2 = new Thread(new SyncThread(), "B_thread2");
//		Thread C_thread1 = new Thread(new SyncThread(), "C_thread1");
//		Thread C_thread2 = new Thread(new SyncThread(), "C_thread2");
//		A_thread1.start();
//		A_thread2.start();
		B_thread1.start();
		B_thread2.start();
//		C_thread1.start();
//		C_thread2.start();
	}
}