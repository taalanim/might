package lift;

public class LiftSimulatorMain {
	private static int startPeps = 7;

	public static void main(String[] args) {
		LiftView v = new LiftView();

		Monitor mon = new Monitor(v);

		Lift lift = new Lift(mon, v);

		Person[] people = new Person[startPeps];
		for (int i = 0; i < startPeps; i++) {
			people[i] = new Person(mon);
			people[i].start();
		}
//		Person p1 = new Person(mon, 4, 5);
//		p1.start();
//		Person p2 = new Person(mon, 2, 3);
//		p2.start();
//		Person p3 = new Person(mon, 1, 3);
//		p3.start();
//		Person p4 = new Person(mon, 1, 4);
//		p4.start();
//		Person p5 = new Person(mon, 1, 5);
//		p5.start();

		lift.start();

	}

}
