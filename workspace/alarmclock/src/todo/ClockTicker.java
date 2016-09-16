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

	public ClockTicker(AlarmClock ac, ClockOutput co, int alarmc) {
		this.ac = ac;
		outsem = ac.getSemaphoreInstance();
		this.output = co;
		this.Alarmcounter= alarmc;
		time = 0;
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
					sleep(700 - time());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				try {
					Thread.sleep(1000 - time());
					time = System.currentTimeMillis();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("tick");
			ac.showTime();
		}
	}
	private long time(){  // I ruv u
		long temp = time;
		time = System.currentTimeMillis();
		if(time - temp > 1000){
			return 999;
		}
		return time - temp;
	}

	

}
