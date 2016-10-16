/*
 * Real-time and concurrent programming course, laboratory 3
 * Department of Computer Science, Lund Institute of Technology
 *
 * PP 980923 Created
 * PP 990924 Revised
 */

package todo;

import done.*;

import se.lth.cs.realtime.*;

/**
 * Superclass for washing programs.
 *
 * <P>
 * If you use this class as a superclass for washing programs, you can access
 * your TemperatureController, your WaterController, and your SpinController
 * through the reference attributes myTempController, myWaterController, and
 * mySpinController, respectively. In addition, you get attributes for the
 * machine you're controlling (myMachine) and the simulation speed (mySpeed).
 * </P>
 *
 * <P>
 * The run() method calls the method wash(), which should be declared and
 * implemented in the subclasses for the actual washing programs in the
 * following way:
 * </P>
 *
 * <P>
 * <CODE>protected void wash() throws InterruptedException { ... }</CODE>
 * </P>
 *
 * <P>
 * This means that your wash() method does not need to catch
 * InterruptedException; it will be handled by the method that called it (run()
 * in this case). The run() method will respond to that exception by turning off
 * all motors and pumps.
 * </P>
 */
public abstract class WashingProgram extends RTThread {

	// ----------------------------------------------------------- CONSTRUCTOR

	/**
	 * @param mach
	 *            The washing machine to control
	 * @param speed
	 *            Simulation speed
	 * @param tempController
	 *            The TemperatureController to use
	 * @param waterController
	 *            The WaterController to use
	 * @param spinController
	 *            The SpinController to use
	 */
	protected WashingProgram(ControlUnit cu, double speed, TemperatureController tempController,
			WaterController waterController, SpinController spinController) {
		super();

		this.cu = cu;
		mySpeed = speed;
		this.sec = (long) (1000 / mySpeed);
		temp = tempController;
		water = waterController;
		spin = spinController;
	}

	// ---------------------------------------------------- OVERRIDDEN METHODS

	/**
	 * This run() method does two things.
	 * <OL>
	 * <LI>Call the wash() method implemented in the subclass.
	 * <LI>If wash() was interrupted, turn all off machinery.
	 * </OL>
	 */
	public void run() {
		boolean wasInterrupted = false;
		try {
			wash();
		} catch (InterruptedException e) {
			wasInterrupted = true;
		} catch (RTInterrupted e) { // Thrown by semaphores
			wasInterrupted = true;
		}

		if (wasInterrupted) {
			System.out.println("Washing program aborted.");
			temp.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 0.0));
			water.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.0));
			spin.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		}
	}

	// ------------------------------------------------------ ABSTRACT METHODS

	/**
	 * Called by run() to perform the actual washing stuff. Should be
	 * implemented by the subclass. Does not need to catch InterruptedException.
	 */
	abstract protected void wash() throws InterruptedException;

	// ------------------------------------------ PROTECTED INSTANCE VARIABLES

	/**
	 * The machine to regulate
	 */
	protected ControlUnit cu;

	/**
	 * Simulation speed
	 */
	protected double mySpeed;

	/**
	 * The temperature controller
	 */
	protected TemperatureController temp;

	/**
	 * The water controller
	 */
	protected WaterController water;

	/**
	 * The spin controller
	 */
	protected SpinController spin;
	protected long sec;

	protected void fillSpinDrain(double heatingTo, long time) {
		// let water into the machine
		water.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.9));
		mailbox.doFetch();

		// heat to 40C, keep the temperature for 15 minutes
		temp.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_SET, heatingTo));
		spin.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
		mailbox.doFetch();
		sleep(time * 60 * sec);
		temp.putEvent(new TemperatureEvent(this, TemperatureEvent.TEMP_IDLE, 0.0));
		spin.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		mailbox.doFetch();

		// drain
		water.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0.0));
		mailbox.doFetch();
		water.putEvent(new WaterEvent(this, WaterEvent.WATER_IDLE, 0.0));
		mailbox.doFetch();
	}

	protected void rinseAndCentrifuge() {
		// rinse 5 times 2 minutes in cold water
		for (int i = 0; i < 5; i++) {
			water.putEvent(new WaterEvent(this, WaterEvent.WATER_FILL, 0.9));
			mailbox.doFetch();
			spin.putEvent(new SpinEvent(this, SpinEvent.SPIN_SLOW));
			sleep(2 * 60 * sec);
			spin.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
			water.putEvent(new WaterEvent(this, WaterEvent.WATER_DRAIN, 0.0));
			mailbox.doFetch();
		}

		// centrifuge 5 minutes
		spin.putEvent(new SpinEvent(this, SpinEvent.SPIN_FAST));
		sleep(5 * 60 * sec);
		spin.putEvent(new SpinEvent(this, SpinEvent.SPIN_OFF));
		mailbox.doFetch();
	}

}
