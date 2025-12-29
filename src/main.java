
public class Main {
	public static void main(String[] args) {
		// first lets make just a simple simulation with earth and the sun to make sure the math is correct
		Simulation sim = new Simulation (500.0, 500.0, 0.001);
		// create the planets
		Planet sun = new Planet(0.0, 0.0, 333_000.0, new Vector(0.0, 0.0));
		Planet earth = new Planet(150.0, 0.0, 1.0, new Vector(0.0, 47.14));
		// add planets to the sim
		sim.addPlanet(sun);
		sim.addPlanet(earth);
		int i = 0;
		while (i < 100000) {
			if (i % 1000 == 0) {
				System.out.println("Step: " + i + " " + sun);
				System.out.println("Step: " + i + " " + earth);
			}
			sim.update();
			i++;
		}
	}
}
