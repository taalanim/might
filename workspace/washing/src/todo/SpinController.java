package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

///** Turn off motor. */
// SPIN_OFF  = 0;
///** Slow spin, changing direction periodically. */
// SPIN_SLOW = 1;
///** Fast spin. */
// SPIN_FAST = 2;

public class SpinController extends PeriodicThread {
	private ControlUnit cu;
	private int lastMode;
	private SpinEvent lastOrder;
	private SpinEvent we;
	private int dirrection = 1;
	private int theoreticalPeriod = 10, counter = -1;

	public SpinController(ControlUnit cu, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.cu = cu;
	}

	public void perform() {
		counter++;
		we = (SpinEvent) mailbox.tryFetch();

		if (we != null) {
			lastOrder = we;
			lastMode = we.getMode();

		}

		switch (lastMode) {

		// Turn off motor.
		case SpinEvent.SPIN_OFF:

			cu.setSpin(SpinEvent.SPIN_OFF);

			break;

		// Slow spin, changing direction periodically.
		case SpinEvent.SPIN_SLOW:

			if (counter == theoreticalPeriod) {
				cu.setSpin(dirr());
				counter = 0;
			}

			break;

		// Fast spin.
		case SpinEvent.SPIN_FAST:

			cu.setSpin(SpinEvent.SPIN_FAST);

			break;

		}
	}

	private int dirr() {
		if (dirrection == 1) {
			dirrection = 2;
			return 1;

		} else {
			dirrection = 1;
			return 2;

		}

	}
}
