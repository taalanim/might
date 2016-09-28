package lift;

public class Monitor {
	private int loc = 0;
	private int nextLoc = 1;
	private int[] pepsWaitingAtFloor = new int[7];
	private int[] pepsWaitingForFloor = new int[7];
	private int pepsInside = 0;
	private LiftView view;

	public Monitor() {

		this.view = new LiftView();

	}

	public synchronized void moveElevator() {

		view.moveLift(loc, nextLoc);
		calcNextLoc();
		notifyAll();
		while ((pepsWaitingForFloor[loc] != 0) || (pepsWaitingAtFloor[loc] != 0 && pepsInside < 4)) {
			try {
				System.out.println("waiting lift");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void calcNextLoc() {
		int oldLoc = loc;
		loc = nextLoc;
		if (loc == 6) {
			nextLoc = 5;
		} else if (loc == 0) {
			nextLoc = 1;
		} else {
			nextLoc += (loc - oldLoc);
		}

	}

	public synchronized Boolean shouldIEnter(int floor, int destination) {
		pepsWaitingAtFloor[floor]++;
		view.drawLevel(floor, pepsWaitingAtFloor[floor]);
		while ((pepsInside == 4) || floor != loc) { // do waiting
			try {
				// System.out.println("waiting to go in \npeps:" + pepsInside +
				// " floor: " + floor+ " location: " +loc);
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} // done waiting
		pepsWaitingForFloor[destination]++;
		pepsWaitingAtFloor[floor]--;
		pepsInside++;
		view.drawLift(loc, pepsInside);
		view.drawLevel(floor, pepsWaitingAtFloor[floor]);
		notifyAll();
		return true;
	}

	public synchronized Boolean shouldIExit(int destination) {
		while (loc != destination) { // do waiting
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} // done waiting
		pepsInside--;
		pepsWaitingForFloor[loc]--;
		view.drawLift(loc, pepsInside);
		view.drawLevel(loc, pepsWaitingAtFloor[loc]);
		notifyAll();
		return false;
	}

}
