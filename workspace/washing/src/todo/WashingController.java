package todo;

import done.*;

public class WashingController implements ButtonListener {
	// TODO: add suitable attributes
	private WaterController wc;
	private TemperatureController tc;
	private SpinController sc;

	private ControlUnit cu;
	public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
		cu = new ControlUnit(theMachine);
		wc = new WaterController(cu, theSpeed);
		tc = new TemperatureController(cu, theSpeed);
		sc = new SpinController(cu, theSpeed);
	}

	public void processButton(int theButton) {
		switch (theButton) {

		// Lock the hatch
		// let water into the machine
		// heat to 60C, keep the temperature for 30 minutes
		// drain
		// rinse 5 times 2 minutes in cold water
		// centrifuge 5 minutes
		// unlock the hatch.
		case 1:

			break;

		// Lock the hatch
		// let water into the machine
		// heat to 40C, keep the temperature for 15 minutes
		// drain
		// let water into the machine
		// heat to 90C, keep the temperature for 30 minutes
		// drain
		// rinse 5 times 2 minutes in cold water
		// centrifuge 5 minutes
		// unlock the hatch.
		case 2:

			break;

		// Turn off heating and rotation
		// pump out the water (if any)
		// unlock the hatch.
		case 3:

			break;
		// stop all
		case 0:

			break;
		}

	}
}
