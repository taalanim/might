package todo;

import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;
import se.lth.cs.realtime.semaphore.MutexSem;

public class AlarmClock {

	private static ClockInput input;
	private static ClockOutput output;
	private int s = 0;
	private int m = 0;
	private int h = 0;
	private int as = 0;
	private int am = 0;
	private int ah = 0;

	private int Alarmcounter = 0;

	public boolean Ringing = false;
	public boolean AlarmFlag = false;

	private Semaphore timeSem;

	public AlarmClock(ClockInput i, ClockOutput o) {
		input = i;
		output = o;
		this.timeSem = new MutexSem();
	}

	public void start() {
		StateMachine sm = new StateMachine(this, input);
		ClockTicker CT = new ClockTicker(this, output, Alarmcounter);
		CT.start();
		sm.start();
	}

	public void showTime() {
		timeSem.take();
		updateTime();
		output.showTime((h * 10000) + (m * 100) + (s));

		if (!Ringing) {
			alarmTest();
		} 
		if (Ringing){
			Alarmcounter++;
			if (Alarmcounter >= 20) {
				resetAlarm();
			}
			output.doAlarm();
		}

		timeSem.give();
	}

	private void alarmTest() {
		if (ah == h && am == m && as == s && AlarmFlag) {
			Ringing = true;

		}
	}



	public void setTime(int time) {
		timeSem.take();
		int temp = time;
		h = temp / 10000;
		temp -= h * 10000;
		m = temp / 100;
		temp -= m * 100;
		s = temp;
		// timerSetter(h, m, s, time);
		timeSem.give();
	}

	public void setAlarmTime(int value) {
		timeSem.take();
		int temp = value;
		ah = temp / 10000;
		temp -= ah * 10000;
		am = temp / 100;
		temp -= am * 100;
		as = temp;
		// timerSetter(ah,am,as,value);
		timeSem.give();
	}

	public void resetAlarm() {
		timeSem.take();
		Alarmcounter = 0;
		Ringing = false;
		timeSem.give();
	}

	private void updateTime() {

		s++;
		if (s >= 60) {
			s = 0;
			m += 1;

			if (m >= 60) {
				m = 0;
				h += 1;

				if (h >= 24) {
					s = h = m = 0;
				}
			}
		}

	}
	// slight change
}
