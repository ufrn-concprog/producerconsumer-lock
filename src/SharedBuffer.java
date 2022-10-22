import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A bounded buffer to be shared by concurrent threads.<br/>
 * The methods for inserting into and removing from the buffer run
 * (upon threads) in mutual exclusion.
 *
 * @author <a href="mailto:everton.cavalcante@ufrn.br">Everton Cavalcante</a>
 */
public class SharedBuffer {
	/** Buffer's capacity */
	private int capacity;

	/** Buffer (implemented as a queue to comply with the problem's constraints) */
	private Queue<Integer> buffer;

	/** Lock object for controlling mutual exclusion */
	private Lock lock;

	/**
	 * Condition variable for suspending/resuming thread execution
	 * when the buffer is full
	 */
	private Condition isFull;

	/**
	 * Condition variable for suspending/resuming thread execution
	 * when the buffer is empty
	 */
	private Condition isEmpty;

	/**
	 * Parameterized constructor
	 * @param capacity Buffer's capacity
	 */
	public SharedBuffer(int capacity) {
		this.capacity = capacity;
		buffer = new LinkedList<Integer>();
		lock = new ReentrantLock(true);
		isFull = lock.newCondition();
		isEmpty = lock.newCondition();
	}


	/**
	 * Inserts an item at the end of the buffer.<br/>
	 * If the buffer achieved its maximum capacity, then the running producer thread
	 * is suspended on the respective condition variable, otherwise a
	 * consumer thread eventually suspended is notified for resuming execution.
	 * @param item Item to be inserted
	 */
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


	/**
	 * Removes the item at the front of the buffer.<br/>
	 * If the buffer is currently empty, then the running consumer thread is suspended
	 * on the respective condition variable, otherwise a
	 * producer thread eventually suspended is notified for resuming execution.
	 */
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
