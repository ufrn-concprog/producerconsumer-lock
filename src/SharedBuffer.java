import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SharedBuffer {
	private int capacity;
	private Queue<Integer> buffer;
	private Lock lock;
	private Condition isFull;
	private Condition isEmpty;
	
	public SharedBuffer(int capacity) {
		this.capacity = capacity;
		buffer = new LinkedList<Integer>();
		lock = new ReentrantLock(true);
		isFull = lock.newCondition();
		isEmpty = lock.newCondition();
	}
	
	
	public void insert(int item) {
		lock.lock();
		try {
			while (buffer.size() == capacity) {
				System.out.print("Buffer is full. ");
				System.out.print(Thread.currentThread().getName() + 
					" suspended.\n");
				isFull.await();
			}
			
			buffer.add(item);
			System.out.println(Thread.currentThread().getName() + 
				" inserted " + item);
			isEmpty.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	
	public void remove() {
		lock.lock();
		try {
			while (buffer.size() == 0) {
				System.out.print("Buffer is empty. ");
				System.out.print(Thread.currentThread().getName() + 
					" suspended.\n");
				isEmpty.await();
			}
			
			int item = buffer.remove();
			System.out.println(Thread.currentThread().getName() + 
				" removed " + item);
			isFull.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}
