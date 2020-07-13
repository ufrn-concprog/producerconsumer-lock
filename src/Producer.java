
public class Producer extends Thread {
	private SharedBuffer buffer;
	
	public Producer(String name, SharedBuffer buffer) {
		super(name);
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		int item = (int) (Math.random() * 100) + 1;
		buffer.insert(item);
	}
}
