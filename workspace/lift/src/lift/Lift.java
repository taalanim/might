package lift;

public class Lift extends Thread {
	private Monitor monitor;

	public Lift(Monitor mon) {
		this.monitor = mon;
	}

	public void run() {
		try {
			sleep(1000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		while (true) {

			while (!monitor.shouldIStay()) {
				monitor.moveElevator();
			}
			try {
				sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
