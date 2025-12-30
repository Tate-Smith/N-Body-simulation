/*
 * Purpose: This is the simulation class that stores all the planets in the sim
 * and performs all the calculations on them
 */

import java.util.ArrayList;

public class Simulation {
	private double step;
	private double G;
	private ArrayList<Planet> planets;
	
	public Simulation(double step, double G) {
		this.step = step;
		this.G = G;
		planets = new ArrayList<>();
	}
	
	public void addPlanet(Planet p) {
		planets.add(p);
	}
	
	public ArrayList<Planet> getPlanets() {
		return new ArrayList<Planet>(this.planets);
	}
	
	public void update() {
		for (Planet p1 : planets) {
			Vector a = new Vector(0.0, 0.0);
			for (Planet p2 : planets) {
				if (p1 != p2) {
					// if they arent the same planet apply the gravity of p2 to p1
					// A = G(p2.mass / distance^2) * direction
					// get the distance vector 
					Vector position = new Vector(p1.getPosition());
					position.sub(p2.getPosition());
					// get the distance between the planets and direction
					double distance = position.magnitude();
					Vector scale = position.normalize();
					// do the equation
					double temp = G * (p2.getMass() / Math.pow(distance, 2));
					scale.scale(temp);
					// add to the total acceleration
					a.add(scale);
				}
			}
			// update this planets velocity
			a.scale(step);
			Vector velocity = new Vector(p1.getVelocity());
			velocity.add(a);
			p1.setVelocity(velocity);
			// now update its position
			velocity.scale(step);
			Vector position = new Vector(p1.getPosition());
			position.add(velocity);
			p1.setPosition(position);
		}
	}
}
