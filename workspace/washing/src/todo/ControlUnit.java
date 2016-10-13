package todo;

import done.AbstractWashingMachine;

//1. The drain pump must not be running while the input valve is open.
//2. The machine must not be heated while it is free of water.
//3. There must not be any water in the machine when the hatch is unlocked.
//4. Centrifuging must not be performed when there is any measurable amounts
//of water in the machine.

public class ControlUnit {
	private AbstractWashingMachine wM;
	private boolean input = false, output = false;

	public ControlUnit(AbstractWashingMachine a) {
		wM = a;
	}

	// 1
	public boolean setFill(boolean on) {
		if (on && output) {
			return false;
		}
		wM.setFill(on);
		return true;
	}

	// 1
	public boolean setDrain(boolean on) {
		if (on && input) {
			return false;
		}
		wM.setDrain(on);
		return true;
	}

	// 2
	public boolean heat(boolean on) {
		if (on && wM.getWaterLevel() == 0) {
			return false;
		}

		wM.setHeating(on);
		return true;
	}

	// 3
	public boolean openDoor(boolean lock) {
		if (!lock && wM.getWaterLevel() != 0) {
			return false;
		}

		wM.setLock(lock);
		return true;
	}

	// 4
	// SPIN_FAST = 3;
	public boolean spin(int dirr) {
		if (dirr == 3 && wM.getWaterLevel() != 0) {
			return false;
		}

		wM.setSpin(dirr);
		return true;
	}
}