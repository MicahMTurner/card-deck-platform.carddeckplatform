package communication.link;


public abstract class Receiver implements Runnable {
	public abstract boolean closeStream();

	public abstract void initializeMode();
}

