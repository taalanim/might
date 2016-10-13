package todo;


import se.lth.cs.realtime.*;
import done.AbstractWashingMachine;


public class SpinController extends PeriodicThread {
	private ControlUnit cu;

	public SpinController(ControlUnit cu, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		this.cu = cu;
	}

	public void perform() {
		// TODO: implement this method
	}
}
