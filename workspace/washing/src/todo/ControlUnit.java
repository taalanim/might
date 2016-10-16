package todo;

import done.AbstractWashingMachine;

//1. The drain pump must not be running while the input valve is open.
//2. The machine must not be heated while it is free of water.
//3. There must not be any water in the machine when the hatch is unlocked.
//4. Centrifuging must not be performed when there is any measurable amounts
//of water in the machine.

public class ControlUnit {
	private AbstractWashingMachine wm;
	private boolean input = false, output = false, isHeatOn = false;

	public ControlUnit(AbstractWashingMachine a) {
		wm = a;
	}

	// 1
	public boolean setFill(boolean on) {
		if (on && output) {
			return false;
		}
		wm.setFill(on);
		return true;
	}

	// 1
	public boolean setDrain(boolean on) {// obs if heating is on
		if (on && input || on && wm.getWaterLevel() <= 0.1) {
			return false;
		}
		wm.setDrain(on);
		return true;
	}

	// 2
	public boolean setHeating(boolean on) {
		if (on && wm.getWaterLevel() == 0) {
			return false;
		}

		wm.setHeating(on);
		isHeatOn = on;
		return true;
	}

	// 3
	public boolean setLock(boolean lock) {
		if (!lock && wm.getWaterLevel() != 0) {
			return false;
		}

		wm.setLock(lock);
		return true;
	}

	// 4
	// SPIN_FAST = 3;
	public boolean setSpin(int dirr) {
		if (dirr == 3 && wm.getWaterLevel() != 0) {
			return false;
		}

		wm.setSpin(dirr);
		return true;
	}

	public double getTemperature() {
		return wm.getTemperature();
	}

	public double getWaterLevel() {
		return wm.getWaterLevel();
	}

	public boolean isLocked() {
		return wm.isLocked();
	}
	public boolean isHeatOn() {
		return isHeatOn;
	}
}