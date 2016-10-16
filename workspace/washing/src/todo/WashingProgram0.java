package todo;

import done.*;
class WashingProgram0 extends WashingProgram {

	// ------------------------------------------------------------- CONSTRUCTOR

	public WashingProgram0(ControlUnit cu, double speed, TemperatureController tempController,
			WaterController waterController, SpinController spinController) {
		super(cu, speed, tempController, waterController, spinController);
	}

	@Override
	protected void wash() throws InterruptedException {/// might not need this class
		// stop all

		// heating off
		temp.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 0.0));

		// spin off
		spin.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		
		// fill off, drain off
		water.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.0));

	}

	// ---------------------------------------------------------- PUBLIC METHODS

}