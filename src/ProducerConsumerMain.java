/**
 * Implementation of a concurrent solution to the
 * <a href="https://en.wikipedia.org/wiki/Producer–consumer_problem">producer-consumer</a>
 * problem using lock objects and condition variables
 *
 * @author <a href="mailto:everton.cavalcante@ufrn.br">Everton Cavalcante</a>
 */
public class ProducerConsumerMain {
	/** Capacity of the bounded buffer */
	private static final int CAPACITY = 100;

	/** Number of producer and consumer threads */
	private static final int NUM_THREADS = 3;

	/**
	 * Main method
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		SharedBuffer buffer = new SharedBuffer(CAPACITY);
		
		Producer producers[] = new Producer[NUM_THREADS];
		Consumer consumers[] = new Consumer[NUM_THREADS];
		for (int i = 0; i < NUM_THREADS; i++) {
			producers[i] = new Producer("Producer " + (i+1), buffer);
			consumers[i] = new Consumer("Consumer " + (i+1), buffer);
		}
		
		for (int i = 0; i < NUM_THREADS; i++) {
			producers[i].start();
			consumers[i].start();
		}

		try {
			for (int i = 0; i < NUM_THREADS; i++) {
				producers[i].join();
				consumers[i].join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
