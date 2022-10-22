/**
 * A consumer thread
 *
 * @author <a href="mailto:everton.cavalcante@ufrn.br">Everton Cavalcante</a>
 */
public class Consumer extends Thread {
	/** Reference to the shared buffer */
	private SharedBuffer buffer;

	/**
	 * Parameterized constructor
	 * @param name Name to be assigned to the thread
	 * @param buffer Reference to the shared buffer
	 */
	public Consumer(String name, SharedBuffer buffer) {
		super(name);
		this.buffer = buffer;
	}

	/** Removes an item from the buffer */
	@Override
	public void run() {
		buffer.remove();
	}
}