package lift;

public class Monitor {
	private int loc = 0;
	private int nextLoc = 1;
	private int[] waitsAtFloors = new int[6];
	private int[] pepsWaitingForFloor = new int[6];
	private int pepsInside = 0;
	private LiftView view;

	public Monitor(LiftView view) {
		this.view = view;

	}

	public synchronized void moveElevator() {

		view.moveLift(loc, nextLoc);
		calcNextLoc();

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

}
