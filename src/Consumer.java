
public class Consumer extends Thread {
	private SharedBuffer buffer;
	
	public Consumer(String name, SharedBuffer buffer) {
		super(name);
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		buffer.remove();
	}
}
