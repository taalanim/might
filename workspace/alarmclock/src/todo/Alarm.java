package todo;//

import done.ClockOutput;

public class Alarm extends Thread {
	private ClockOutput output;
	public Alarm(ClockOutput co){
		this.output = output;
	}
	public void run(){
		output.doAlarm();
		try {
			sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
