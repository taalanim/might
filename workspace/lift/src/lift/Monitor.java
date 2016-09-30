package lift;

public class Monitor {
	private int here = 0;
	private int next = 0;
	private int[] pepsWaitingAtFloor = new int[7];
	private int[] pepsWaitingForFloor = new int[7];
	private int pepsInside = 0, pepsTot = 0, dirr = 1;
	boolean moving = false;

	private LiftView view;

	public Monitor(LiftView v) {

		this.view = v;

	}

	public synchronized int movementSystem() {
		moving = false;
		here = next;

		while ((pepsWaitingForFloor[here] != 0) || (pepsWaitingAtFloor[here] != 0 && pepsInside < 4)
				|| (pepsTot == 0)) {
			try {
			//	System.out.println("in wait   :  " + here + "   " + next);
				notifyAll();
				wait();
				//System.out.println("out of wait   :  " + here + "   " + next);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	//	System.out.println("movement");

		calcNextLoc();
		notifyAll();
		moving = true;
		return next;

	}

	private void calcNextLoc() {
		if (here == 6) {
			dirr = -1;
		} else if (here == 0) {
			dirr = 1;
		}
		next += dirr;

	}

	public synchronized void shouldIEnter(int floor, int destination) {
		pepsWaitingAtFloor[floor]++;
		pepsTot++;
		view.drawLevel(floor, pepsWaitingAtFloor[floor]);

		while ((pepsInside == 4) || floor != here||moving) { // do waiting
			try {
			//	System.out.println("- im in wait  : person");
				notifyAll();
				wait();
			//	System.out.println("- Im out of the wait  : person");

			} catch (InterruptedException e) {
				System.out.println("- Im got interuppted : person");
				e.printStackTrace();
			}

		} // done waiting

		pepsWaitingForFloor[destination]++;
		pepsWaitingAtFloor[floor]--;
		pepsInside++;
		view.drawLevel(floor, pepsWaitingAtFloor[floor]);
		view.drawLift(here, pepsInside);
		notifyAll();
	}

	public synchronized void shouldIExit(int destination) {
	
		while (here != destination) { // do waiting
			try {
				notifyAll();
				wait();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		} // done waiting
		pepsInside--;
		pepsWaitingForFloor[here]--;
		pepsTot--;
		view.drawLift(here, pepsInside);
		notifyAll();
	}

}
