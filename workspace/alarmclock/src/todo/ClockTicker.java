package todo;

import done.ClockInput;
import done.ClockOutput;
import se.lth.cs.realtime.semaphore.Semaphore;

public class ClockTicker extends Thread {
	private AlarmClock ac;
	private Semaphore outsem;

	public ClockTicker(AlarmClock ac) {
		this.ac = ac;
		outsem = ac.getSemaphoreInstance();
		
	}

	public void run() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			outsem.take();
			updateTime();
			System.out.println("tick");
			ac.showTime();
			outsem.give();
		}
	}

	private void updateTime() {

		ac.s++;
		if (ac.s >= 60) {
			ac.s = 0;
			ac.m += 1;

			if (ac.m >= 60) {
				ac.m = 0;
				ac.h += 1;

				if (ac.h >= 24) {
					ac.s = ac.h = ac.m = 0;
				}
			}
		}

	}

}
