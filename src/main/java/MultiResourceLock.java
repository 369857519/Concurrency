public class MultiResourceLock {

	public static void main(String[] args) throws InterruptedException {
		AccountStupidLock accountStupidLock = new AccountStupidLock();
		AccountStupidLock accountStupidLock1 = new AccountStupidLock();
		AccountStupidLock accountStupidLock2 =new AccountStupidLock();
		Thread thread1 = new Thread(() -> {
			accountStupidLock.transfer(accountStupidLock1,100);
		});
		Thread thread2=new Thread(()->{
			accountStupidLock1.transfer(accountStupidLock2,100);
		});
		thread2.start();
		thread1.start();
		thread1.join();
		thread2.join();
		System.out.println(accountStupidLock.getBalance());
		System.out.println(accountStupidLock1.getBalance());
		System.out.println(accountStupidLock2.getBalance());
	}
}

/**
 * 总结：对象锁在统一对象指针下有用 静态对象锁全局有用，同时能保持锁的私有性 静态class锁在全局有用 synchronized在实例修饰方法的时候比较危险，因为用的是this指针，在两个不同的实例中互相传引用时，会出现问题。
 */

/**
 * 细粒度锁
 */
class Account {

	// 锁：保护账户余额
	private final Object balLock
		= new Object();
	// 账户余额
	private Integer balance;
	// 锁：保护账户密码
	private final Object pwLock
		= new Object();
	// 账户密码
	private String password;

	// 取款
	void withdraw(Integer amt) {
		synchronized (balLock) {
			if (this.balance > amt) {
				this.balance -= amt;
			}
		}
	}

	// 查看余额
	Integer getBalance() {
		synchronized (balLock) {
			return balance;
		}
	}

	// 更改密码
	void updatePassword(String pw) {
		synchronized (pwLock) {
			this.password = pw;
		}
	}

	// 查看密码
	String getPassword() {
		synchronized (pwLock) {
			return password;
		}
	}
}


//多对象访问安全。在多个Account之间调用时有用
class AccountStaticLock {

	private static Object lock = new Object();
	private int balance;

	// 转账
	void transfer(AccountStaticLock target, int amt) {
		synchronized (lock) {
			if (this.balance > amt) {
				this.balance -= amt;
				target.balance += amt;
			}
		}
	}
}

class AccountStupidLock {

	private int balance=200;

	public int getBalance(){
		return balance;
	}

	// 转账
	synchronized void transfer(AccountStupidLock target, int amt) {
		if (this.balance > amt) {
			this.balance -= amt;
			target.balance += amt;
		}
	}
}