package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;


public class TemperatureController extends PeriodicThread {
	// TODO: add suitable attributes
private ControlUnit cu;
	public TemperatureController(ControlUnit cu, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		this.cu = cu;
		
	}

	public void perform(int temp) {
		
		// TODO: implement this method
	}
}
