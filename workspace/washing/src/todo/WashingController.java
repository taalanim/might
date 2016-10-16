package todo;

import done.*;

public class WashingController implements ButtonListener {
	// TODO: add suitable attributes
	private WaterController wc;
	private TemperatureController tc;
	private SpinController sc;
	private WashingProgram activeProgram;
	private double speed;
	private boolean changable = true;

	private ControlUnit cu;

	public WashingController(AbstractWashingMachine theMachine, double theSpeed) {
		cu = new ControlUnit(theMachine);
		wc = new WaterController(cu, theSpeed);
		tc = new TemperatureController(cu, theSpeed);
		sc = new SpinController(cu, theSpeed);
		speed = theSpeed;
	}

	public void processButton(int theButton) {
		switch (theButton) {

		case 1:
			if (changable) {
				activeProgram = new WashingProgram1(cu, speed, tc, wc, sc);
				changable = false;
			}
			break;

		case 2:
			if (changable) {
				activeProgram = new WashingProgram2(cu, speed, tc, wc, sc);
				changable = false;
			}
			break;

		case 3:// rules???????
			if (changable) {
				activeProgram = new WashingProgram3(cu, speed, tc, wc, sc);
				changable = false;
			}
			break;

		case 0:

			if (activeProgram.isAlive()) {
				activeProgram.interrupt();
			}
			changable = true;
			break;
		}

	}
}
