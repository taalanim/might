package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;

///** Temperature regulation off (no heating) */
//TEMP_IDLE  = 0;
///** Reach and hold the given temperature */
//TEMP_SET   = 1;

public class TemperatureController extends PeriodicThread {
	// TODO: add suitable attributes
	private ControlUnit cu;
	private int lastMode;
	private TemperatureEvent lastOrder;
	private TemperatureEvent we;
	private boolean acksent = false;
	private double lastTemp;
	private double power = 4200, rumsTemp = 20;
	private int theoreticalPeriod = 10, counter = -1;

	public TemperatureController(ControlUnit cu, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.cu = cu;
	}

	public void perform() { // = 1 second

		counter++;
		we = (TemperatureEvent) mailbox.tryFetch();

		if (we != null) {
			acksent = false;
			lastOrder = we;
			lastMode = we.getMode();
			lastTemp = we.getTemperature();

		}

		switch (lastMode) {

		// Temperature regulation off (no heating)
		case TemperatureEvent.TEMP_IDLE:

			cu.setHeating(false);

			break;

		// Reach and hold the given temperature
		case TemperatureEvent.TEMP_SET:

			if (counter >= theoreticalPeriod) {
				cu.setHeating(regulator());
				counter = 0;
			}

			break;

		}
	}

	private boolean regulator() { // fungerar inte helt
		// obs, abretar inte med för-värmda system
		int temp = (int) ((10000) * cu.getTemperature());
		double volym = 20 * cu.getWaterLevel();
		int a = (int) ((10000) * (((lastTemp - ((theoreticalPeriod * power) / (volym * 4186.4)))))) + 6000;
		if (cu.isHeatOn()) {
			if (temp >= (a)) {

				sendAck();

				return false;
			}

			return true;
		} else {

			if (temp <= a) {

				return true;
			}
			return false;
		}
	}

	private void sendAck() {
		if (!acksent) {
			((RTThread) lastOrder.getSource()).putEvent(new RTEvent(this));
			acksent = true;
		}
	}
}
