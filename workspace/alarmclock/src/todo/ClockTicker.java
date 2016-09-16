package todo;

import done.ClockInput;
import done.ClockOutput;
import se.lth.cs.realtime.semaphore.Semaphore;

public class ClockTicker extends Thread {
	private AlarmClock ac;
	private boolean ticktock;
	private long t, diff = 0;

	public ClockTicker(AlarmClock ac, ClockOutput co, int alarmc) {
		this.ac = ac;
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
