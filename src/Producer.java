/**
 * A producer thread
 *
 * @author <a href="mailto:everton.cavalcante@ufrn.br">Everton Cavalcante</a>
 */
public class Producer extends Thread {
	/** Reference to the shared buffer */
	private SharedBuffer buffer;

	/**
	 * Parameterized constructor
	 * @param name Name to be assigned to the thread
	 * @param buffer Reference to the shared buffer
	 */
	public Producer(String name, SharedBuffer buffer) {
		super(name);
		this.buffer = buffer;
	}

	/** Inserts an item (a random integer) into the buffer */
	@Override
	public void run() {
		int item = (int) (Math.random() * 100) + 1;
		buffer.insert(item);
	}
}