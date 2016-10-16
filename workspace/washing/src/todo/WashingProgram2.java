package todo;

import done.*;

class WashingProgram2 extends WashingProgram {

	// ------------------------------------------------------------- CONSTRUCTOR

	public WashingProgram2(ControlUnit cu, double speed, TemperatureController tempController,
			WaterController waterController, SpinController spinController) {
		super(cu, speed, tempController, waterController, spinController);
	}

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
	@Override
	protected void wash() throws InterruptedException {

		// Lock the hatch
		cu.setLock(true);

		// let water into the machine
		// heat to 40C, keep the temperature for 15 minutes
		// drain
		fillSpinDrain((double) 40, (long) 15);

		// let water into the machine
		// heat to 90C, keep the temperature for 30 minutes
		// drain
		fillSpinDrain((double) 90, (long) 30);

		// rinse 5 times 2 minutes in cold water
		// centrifuge 5 minutes
		rinseAndCentrifuge();

		// unlock the hatch.
		cu.setLock(false);

	}

	

}