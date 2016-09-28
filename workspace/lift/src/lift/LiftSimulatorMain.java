package lift;

public class LiftSimulatorMain {

	public static void main(String[] args) {

		Monitor mon = new Monitor();

		Lift lift = new Lift(mon);

		Person[] person = new Person[20];
		
		
			System.out.println("making peps");
			Person person1 = new Person(mon);
			person1.run();
			System.out.println("ran");

			System.out.println("run lift");
			lift.run();
			System.out.println("lift ran");
		
	}

}
