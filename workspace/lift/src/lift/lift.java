package lift;

public class lift extends Thread {
	private Monitor monitor;

	public lift(Monitor mon) {
		this.monitor = mon;
	}

	public void run() {
		while (true) {

			monitor.moveElevator();
		}
	}

}
