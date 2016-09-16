package todo;

import se.lth.cs.realtime.semaphore.Semaphore;
import done.ClockInput;

public class StateMachine extends Thread {
	private Semaphore sem;
	private ClockInput input;
	private int choice = 0;
	private int value = 0;
	private AlarmClock ac;
	private boolean settime = false;
	private boolean setAtime = false;

	public StateMachine(AlarmClock ac, ClockInput i) {
		this.input = i;
		this.ac = ac;
		sem = i.getSemaphoreInstance();

	}

	public void run() {
		while (true) {
			sem.take();
			ac.AlarmFlag = input.getAlarmFlag();
			if (ac.Ringing) {
				ac.resetAlarm();
			} else {

				value = input.getValue();
				choice = input.getChoice();

				switch (choice) {
				case 0:
					if (settime) {
						ac.setTime(value);
					} else if (setAtime) {
						ac.setAlarmTime(value);
					}
					settime = false;
					setAtime = false;
					break;
				case 1:
					setAtime = true;
					settime = false;
					break;
				case 2:
					settime = true;
					setAtime = false;
					break;
				}

			}
		}
	}
// slight change
}