package SynchronizTest;

public class NumberServer {


    public static void main(String[] args) {
        Thread thread1=new Thread(new NumberMachine());
        Thread thread2=new Thread(new NumberMachine());
        Thread thread3=new Thread(new NumberMachine());
        Thread thread4=new Thread(new NumberMachine());
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
