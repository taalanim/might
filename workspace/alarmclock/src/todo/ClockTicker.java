package todo;

import done.ClockInput;
import done.ClockOutput;
import se.lth.cs.realtime.semaphore.Semaphore;

public class ClockTicker extends Thread {
	private AlarmClock ac;
	private Semaphore outsem;
	private ClockOutput output;
	public int Alarmcounter = 0;
	
	public ClockTicker(AlarmClock ac,ClockOutput co) {
		this.ac = ac;
		outsem = ac.getSemaphoreInstance();
		this.output = co;
		
	}

	public void run() {
		while (true) {
			if(ac.Ringing)
			{
			Alarmcounter++;
			if(Alarmcounter >= 20){
				ac.resetAlarm();
			}
			output.doAlarm();
			
			try {
				sleep(700);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			}else{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
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
