package lift;

public class Monitor {
	private int loc = 0;
	private int nextLoc = 1;
	private int[] pepsWaitingAtFloor = new int[7];
	private int[] pepsWaitingForFloor = new int[7];
	private int pepsInside = 0;
	private int pepsTot = 0;
	private LiftView view;

	public Monitor() {

		this.view = new LiftView();

	}

	public synchronized boolean shouldIStay() {
		// System.out.println(pepsTot);
		return (pepsTot == 0);
	}

	public void moveElevator() {
		view.moveLift(loc, nextLoc);
		movementSystem();
	}

	private synchronized void movementSystem() {
		calcNextLoc();
		notifyAll();
		while ((pepsWaitingForFloor[loc] != 0) || (pepsWaitingAtFloor[loc] != 0 && pepsInside < 4)) {
			try {
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

	public synchronized void shouldIEnter(int floor, int destination) {
		pepsWaitingAtFloor[floor]++;
		pepsTot++;
		view.drawLevel(floor, pepsWaitingAtFloor[floor]);

		while ((pepsInside == 4) || floor != loc) { // do waiting
			try {
				wait();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		} // done waiting
		pepsWaitingForFloor[destination]++;
		pepsWaitingAtFloor[floor]--;
		pepsInside++;
		view.drawLevel(floor, pepsWaitingAtFloor[floor]);
		view.drawLift(loc, pepsInside);
		notifyAll();
	}

	public synchronized void shouldIExit(int destination) {
		while (loc != destination) { // do waiting
			try {
				wait();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		} // done waiting
		pepsInside--;
		pepsWaitingForFloor[loc]--;
		pepsTot--;
		view.drawLift(loc, pepsInside);
		notifyAll();
	}

}
