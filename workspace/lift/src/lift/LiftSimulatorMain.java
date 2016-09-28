package lift;

public class LiftSimulatorMain {

	public static void main(String[] args) {

		Monitor mon = new Monitor();

		Lift lift = new Lift(mon);

		
		
		Person[] people = new Person[21];
			for(int i=0;i<21;i++){
				people[i] = new Person(mon);
				people[i].start();
			}
//			Person p1 = new Person(mon,1,6);
//			Person p2 = new Person(mon,1,2);
//			Person p3 = new Person(mon,1,3);
//			Person p4 = new Person(mon,1,4);
//			Person p5 = new Person(mon,1,5);
//			p1.start();
//			p2.start();
//			p3.start();
//			p4.start();
//		p5.start();
			lift.start();
		
	}

}
