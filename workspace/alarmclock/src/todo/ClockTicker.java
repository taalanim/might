package todo;

import done.ClockInput;
import done.ClockOutput;
import se.lth.cs.realtime.semaphore.Semaphore;

public class ClockTicker extends Thread {
	private AlarmClock ac;
	private Semaphore outsem;
	private ClockOutput output;
	private int Alarmcounter;

	public ClockTicker(AlarmClock ac, ClockOutput co, int alarmc) {
		this.ac = ac;
		outsem = ac.getSemaphoreInstance();
		this.output = co;
		this.Alarmcounter= alarmc;

	}

	public void run() {
		while (true) {
			if (ac.Ringing) {
				Alarmcounter++;
				if (Alarmcounter >= 20) {
					ac.resetAlarm();
				}
				output.doAlarm();

				try {
					sleep(700);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("tick");
			ac.showTime();
		}
	}

	

}
