package todo;

import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;

public class WaterController extends PeriodicThread {
	// TODO: add suitable attributes
	/** Regulation off, turn off all pumps */
	// WATER_IDLE=0;

	/** Fill water to a given level */
	// WATER_FILL=1;

	/** Drain, leave drain pump running when finished */
	// WATER_DRAIN=2;

	private ControlUnit cu;
	private int lastMode;
	private double lastLevel;
	private WaterEvent lastOrder;
	private WaterEvent we;
	private boolean acksent = false;

	public WaterController(ControlUnit cu, double speed) {
		super((long) (1000 / speed)); // TODO: replace with suitable period
		this.cu = cu;
	}

	public void perform() {
		we = (WaterEvent) mailbox.tryFetch();

		if (we != null) {
			acksent = false;
			lastOrder = we;
			lastMode = we.getMode();
			lastLevel = we.getLevel();

		}
		if (!acksent) {
			switch (lastMode) {
			case WaterEvent.WATER_IDLE: /** Regulation off, turn off all pumps */
				cu.setDrain(false);
				cu.setFill(false);
				((RTThread) lastOrder.getSource()).putEvent(new RTEvent(this));
				acksent = true;
				break;
			case  WaterEvent.WATER_FILL:/** Fill water to a given level */
				cu.setFill(false);
				cu.setDrain(true);
				if (cu.getWaterLevel() >= lastLevel) {
					cu.setDrain(false);
					((RTThread) lastOrder.getSource()).putEvent(new RTEvent(this));
					acksent = true;
				}
				break;
			case  WaterEvent.WATER_DRAIN:/** Drain, leave drain pump running when finished */
				cu.setFill(false);
				cu.setDrain(true);
				if (lastLevel == 0) {
					((RTThread) lastOrder.getSource()).putEvent(new RTEvent(this));
					acksent = true;
				}

				break;

			}
		}
	}
}
