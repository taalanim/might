package todo;

import done.ClockInput;
import done.ClockOutput;
import se.lth.cs.realtime.semaphore.Semaphore;

public class ClockTicker extends Thread {
	private AlarmClock ac;
	private Semaphore outsem;
	private ClockOutput output;
	private int Alarmcounter;
	private long time;
	private long time2;
	private long lostTime;
	private boolean ticktock;
	private long t, diff = 0;

	public ClockTicker(AlarmClock ac, ClockOutput co, int alarmc) {
		this.ac = ac;

		this.output = co;
		this.Alarmcounter = alarmc;
		time = 0;
		lostTime = 0;
		ticktock = false;
	}

	public void run() {
		t = System.currentTimeMillis();
		while (true) {
			t += 1000;
			diff = t - System.currentTimeMillis();
			if (diff > 0) {
				slep(diff);
			}
			ticktock();
			ac.showTime();
		}
	}

	
	
	
	
	
	private void slep(long diff) {
		try {
			sleep(diff);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ticktock() {
		if (ticktock) {
			System.out.println("tick");
		} else {
			System.out.println("tock");
		}
		ticktock = !ticktock;
	}
	// slight change
}
