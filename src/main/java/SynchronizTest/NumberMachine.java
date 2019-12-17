package SynchronizTest;

public class NumberMachine implements Runnable {

    public static int NUM = 50;

    private static int index = 0;

    @Override
    public void run() {
        synchronized (this) {
            while (index <= NUM) {
                System.out.println(Thread.currentThread().getName() + ":" + index++);
            }
        }
    }
}
