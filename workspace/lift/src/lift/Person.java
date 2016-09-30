package lift;

public class Person extends Thread {

	private int floor;
	private int dest;
	private Boolean inLift = false;
	private Monitor monitor;

	public Person(Monitor mon) {

		this.monitor = mon;
		init();
	}

	public Person(Monitor mon, int i, int j) { // test constructor
		this.monitor = mon;
		floor = i;
		dest = j;
	}

	public void run() {

		while (true) {
			if (!inLift) {
				inLift = monitor.shouldIEnter(floor, dest);
			} else {
				inLift = monitor.shouldIExit(dest);
				slepe();
				init();

			}
		}

	}

	private void init() {
		floor = (int) (7 * Math.random());
		do {
			dest = (int) (7 * Math.random());
		} while (floor == dest);

	}

	private void slepe() {
		try {
			int delay = (int) (Math.random() * 10000);

			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
