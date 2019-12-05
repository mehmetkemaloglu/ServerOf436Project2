
class BinarySemaphore { // used for mutual exclusion
	boolean value;
	public BinarySemaphore(boolean initValue) {
		value = initValue;
	}
	public synchronized void P() throws InterruptedException { // atomic operation // blocking
		while (value == false)
			wait(); // add process to the queue of blocked processes
		value = false;
	}
	public synchronized void V() { // atomic operation // non-blocking
		value = true;
		notify(); // wake up a process from the queue
	}
}