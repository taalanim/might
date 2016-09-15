package todo;//

import done.ClockOutput;
import se.lth.cs.realtime.semaphore.Semaphore;

public class Alarm extends Thread {
	private ClockOutput output;
	
	public Alarm(ClockOutput output2) {
		this.output = output2;
	}
	public void run(){
		while(true){
		output.doAlarm();
		try {
			sleep(700);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}}

}
