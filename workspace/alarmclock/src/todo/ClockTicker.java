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

	
	public ClockTicker(AlarmClock ac, ClockOutput co, int alarmc) {
		this.ac = ac;
		
		this.output = co;
		this.Alarmcounter= alarmc;
		time = 0;
		lostTime = 0;
		ticktock= false;
	}

	public void run() {
		try {
			time2 = System.currentTimeMillis();
			Thread.sleep(1000);
			lostTime = System.currentTimeMillis();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	System.out.println("tick");
	ac.showTime();
		while (true) {
				try {
					time2 = System.currentTimeMillis();
					Thread.sleep(1000 - (time2 - lostTime));
					lostTime = System.currentTimeMillis();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(ticktock){
				System.out.println("tick");
			} else{
				System.out.println("tock");
			}
			ticktock = !ticktock;
			ac.showTime();
		}
	}

	

}
