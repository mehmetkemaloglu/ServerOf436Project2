class CountingSemaphore { // Different from the book
	// used for synchronization of cooperating threads
	int value; // semaphore is initialized to the number of resources
	public CountingSemaphore(int initValue) {
		value = initValue;
	}
	public synchronized void P() throws InterruptedException { // blocking
		
		while (value == 0)
			wait();
		value--;
	}
	public synchronized void V() { // non-blocking
		value++;
		notifyAll();
	}
}