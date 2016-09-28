package lift;

public class Person extends Thread {

	private int floor;
	private int dest;
	private Boolean inLift = false;
	private Monitor monitor;

	public Person(Monitor mon) {

		this.monitor = mon;
	}

	public void run() {
		init();
		System.out.println("inited");
		while (true) {
			if (!inLift) {
				inLift = monitor.shouldIEnter(floor,dest);
			} else {
				inLift = monitor.shouldIExit(dest);
				dontcont(); // remove later

			}
		}

	}

	private void dontcont() {// remove later
		if (!inLift) {
			while (true) {
				try {
					sleep(99999999);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void init() {
		floor = (int) (7 * Math.random());
		do {
			dest = (int) (7 * Math.random());
		} while (floor != dest);
		
	}
}
