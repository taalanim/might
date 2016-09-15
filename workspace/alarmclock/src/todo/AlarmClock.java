package todo;

import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

public class AlarmClock {

	private static ClockInput input;
	private static ClockOutput output;
	public Semaphore outsem;
	public int s = 0;
	public int m = 0;
	public int h = 0;
	public int as = 0;
	public int am = 0;
	public int ah = 0;

	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		this.outsem = new MutexSem();
	}

	public void start() {
		StateMachine sm = new StateMachine(this, input);
		ClockTicker CT = new ClockTicker(this);
		CT.start();
		sm.start();
	}

	public void showTime() {
		output.showTime((h * 10000) + (m * 100) + (s));

	}

	public void setTime(int time) {
		int temp = time;
		h = temp / 10000;
		temp -= h * 10000;
		m = temp / 100;
		temp -= m * 100;
		s = temp;
	}

	public void setAlarmTime(int next) {
		// TODO Auto-generated method stub
		
	}

	public Semaphore getSemaphoreInstance() {
		// TODO Auto-generated method stub
		return outsem;
	}

}
