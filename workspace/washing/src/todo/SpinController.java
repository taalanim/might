package todo;


import se.lth.cs.realtime.*;
import se.lth.cs.realtime.event.RTEvent;
import done.AbstractWashingMachine;


///** Turn off motor. */
// SPIN_OFF  = 0;
///** Slow spin, changing direction periodically. */
// SPIN_SLOW = 1;
///** Fast spin. */
// SPIN_FAST = 2;


public class SpinController extends PeriodicThread {
	private ControlUnit cu;
	private int lastMode;
	private SpinEvent lastOrder;
	private SpinEvent we;
	private boolean acksent = false;
	private int dirrection = 1;

	public SpinController(ControlUnit cu, double speed) {
		super((long) (1000/speed)); // TODO: replace with suitable period
		this.cu = cu;
	}

	public void perform() {
		we = (SpinEvent) mailbox.tryFetch();

		if (we != null) {
			acksent = false;
			lastOrder = we;
			lastMode = we.getMode();

		}
		if (!acksent || lastMode == SpinEvent.SPIN_SLOW) {
			switch (lastMode) {
			case SpinEvent.SPIN_OFF:/** Turn off motor. */
				cu.setSpin(SpinEvent.SPIN_OFF);
				((RTThread) lastOrder.getSource()).putEvent(new RTEvent(this));
				acksent=true;
				
				break;
			case  SpinEvent.SPIN_SLOW:/** Slow spin, changing direction periodically. */
				cu.setSpin(dirr());
				if(!acksent){
				((RTThread) lastOrder.getSource()).putEvent(new RTEvent(this));
				acksent=true;
				}
				
				break;
			case  SpinEvent.SPIN_FAST:/** Fast spin. */
				cu.setSpin(SpinEvent.SPIN_FAST);
				((RTThread) lastOrder.getSource()).putEvent(new RTEvent(this));
				acksent=true;

				break;

			}
			
		}
	}
	private int dirr(){
		if(dirrection == 1){
			dirrection = 2;
			return 1;
			
		}
		else{
			dirrection = 1;
			return 2;
			
		}
	}
}
