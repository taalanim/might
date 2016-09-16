package todo;

import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

public class AlarmClock {

	private static ClockInput input;
	private static ClockOutput output;
	private Semaphore outsem;
	public int s = 0;
	public int m = 0;
	public int h = 0;
	public int as = 0;
	public int am = 0;
	public int ah = 0;
	public boolean Ringing = false;
	public boolean AlarmFlag = false;
	private int alarmcounter;

	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		this.outsem = new MutexSem();

	}

	public void start() {
		StateMachine sm = new StateMachine(this, input);
		ClockTicker CT = new ClockTicker(this, output);
		CT.start();
		sm.start();
	}

	public void showTime() {
		output.showTime((h * 10000) + (m * 100) + (s));
		if (ah == h && am == m && as == s && AlarmFlag) {
			Ringing = true;

		}
	}

	public void setTime(int time) {
		int temp = time;
		h = temp / 10000;
		temp -= h * 10000;
		m = temp / 100;
		temp -= m * 100;
		s = temp;
	}

	public void setAlarmTime(int value) {
		int temp = value;
		ah = temp / 10000;
		temp -= ah * 10000;
		am = temp / 100;
		temp -= am * 100;
		as = temp;
	}

	public Semaphore getSemaphoreInstance() {
		// TODO Auto-generated method stub
		return outsem;
	}

	public void giveAlarmC(int a) {
		alarmcounter = a;

	}

	public void resetAlarm() {
		alarmcounter = 0;
		Ringing = false;
	}
}
