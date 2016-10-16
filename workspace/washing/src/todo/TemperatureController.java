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
	private double period, volym, power = 4200, rumsTemp = 20;

	public TemperatureController(ControlUnit cu, double speed) {
		super((long) (10000 / speed)); // TODO: replace with suitable period
		this.cu = cu;
		this.period = 10000 / speed;
	}

	public void perform() {
		we = (TemperatureEvent) mailbox.tryFetch();

		if (we != null) {
			acksent = false;
			lastOrder = we;
			lastMode = we.getMode();
			lastTemp = we.getTemperature();

		}
		if (!acksent) {
			switch (lastMode) {
			case TemperatureEvent.TEMP_IDLE:/**
											 * Temperature regulation off (no
											 * heating)
											 */
				cu.setHeating(false);
				((RTThread) lastOrder.getSource()).putEvent(new RTEvent(this));
				acksent = true;
				break;
			case TemperatureEvent.TEMP_SET:/**
											 * Reach and hold the given
											 * temperature
											 */
				cu.setHeating(regulator());
				if (!acksent) {
					((RTThread) lastOrder.getSource()).putEvent(new RTEvent(this));
					acksent = true;
				}
				break;

			}
		}
	}

	private boolean regulator() {
		double temp = cu.getTemperature();
		double volym = 10*cu.getWaterLevel();
		if (cu.isHeatOn()) { // up = (period * power) / (volym * 4186.4); // if (temp >= (lastTemp - up))
			if (temp >= (lastTemp - ((period * power) / (volym * 4186.4)))) {
				return false;
			}
			return true;
		} else { // ner = (period * (cu.getTemperature() - rumsTemp)) / (3500); // if (temp <= (lastTemp + ner))
			
			if (temp <= (lastTemp + ((period * (temp - rumsTemp)) / (3500)))) {
				return true;
			}
			return false;
		}
	}
}
