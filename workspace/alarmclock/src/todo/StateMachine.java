package todo;

import se.lth.cs.realtime.semaphore.Semaphore;
import done.ClockInput;
import done.ClockOutput;

public class StateMachine extends Thread {////////////////////////////7
	private Semaphore sem;
	private ClockInput input;
	private Semaphore outsem;
	private int choice = 0;
	private int value = 0;
	private AlarmClock ac;
	private int last = 888888;
	private int next = 0;
	private boolean taken = false;

	public StateMachine(AlarmClock ac, ClockInput i) {
		this.input = i;
		this.ac = ac;
		sem = i.getSemaphoreInstance();
		outsem = ac.getSemaphoreInstance();
	}

	public void run() {
		while (true) {
			sem.take();
			
			
			value = input.getValue();
			choice = input.getChoice();
			
			
			switch (choice) {
			case 0:
				if(taken){
				outsem.give();
				taken = false;}
			break;
			case 1:
				ac.setAlarmTime(next);
				break;
			case 2:
				
				if(!taken){
				outsem.take();
				taken = true;
				}
				ac.setTime(value);
				break;
			}

		}
	}

}