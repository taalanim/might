package lift;

public class Person extends Thread {

	private int floor;
	private int dest;

	public void run() {
		init();

		while (true) {

		}

	}

	private void init() {
		floor = (int) (7 * Math.random());
		do {
			dest = (int) (7 * Math.random());
		} while (floor != dest);
	}
}
