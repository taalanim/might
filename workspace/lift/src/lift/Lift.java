package lift;

public class Lift extends Thread {
	private Monitor monitor;
	private LiftView v;
	private int here = 0, next = 1;

	public Lift(Monitor mon, LiftView v) {
		this.monitor = mon;
		this.v = v;
	}

	public void run() {
		letMeStartFirst();
		while (true) { // the loop

		
			next = monitor.movementSystem();
			v.moveLift(here, next);
			here = next;

			
		}
	}

	private void letMeStartFirst() {
		try {
			sleep(1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

}
