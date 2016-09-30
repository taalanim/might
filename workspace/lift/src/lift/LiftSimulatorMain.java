package lift;

public class LiftSimulatorMain {
	private static int startPeps = 10;

	public static void main(String[] args) {

		Monitor mon = new Monitor();

		Lift lift = new Lift(mon);

		Person[] people = new Person[startPeps];
		for (int i = 0; i < startPeps; i++) {
			people[i] = new Person(mon);
			people[i].start();
		}
//		Person p1 = new Person(mon,2,3);
//		 Person p2 = new Person(mon,2,3);
//		// Person p3 = new Person(mon,1,3);
//		// Person p4 = new Person(mon,1,4);
//		// Person p5 = new Person(mon,1,5);
//		 p1.start();
//		 p2.start();
//		// p3.start();
//		// p4.start();
//		// p5.start();
		lift.start();

	}

}
