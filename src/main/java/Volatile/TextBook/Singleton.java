package Volatile.TextBook;

public class Singleton {
    private volatile static Singleton instance;
    public static Singleton getInstance(){
        if(instance==null){
            synchronized (Singleton.class){
                instance=new Singleton();
            }
        }
        return instance;
    }
}
