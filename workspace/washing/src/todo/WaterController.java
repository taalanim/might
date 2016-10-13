package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;


public class WaterController extends PeriodicThread {
	// TODO: add suitable attributes
private ControlUnit cu;
	public WaterController(ControlUnit cu, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		this.cu = cu;
	}

	public void perform() {
		// TODO: implement this method
	}
}
