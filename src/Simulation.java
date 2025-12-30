/*
 * Purpose: This is the simulation class that stores all the planets in the sim
 * and performs all the calculations on them
 */

import java.util.ArrayList;

public class Simulation {
	private double step;
	private double G;
	private double softening;
	private ArrayList<Planet> planets;
	
	public Simulation(double step, double G, double softening) {
		this.step = step;
		this.G = G;
		this.softening = softening;
		planets = new ArrayList<>();
	}
	
	public void addPlanet(Planet p) {
		planets.add(p);
	}
	
	public ArrayList<Planet> getPlanets() {
		return new ArrayList<Planet>(this.planets);
	}
	
	public void update() {
		/*
		 * This function is what updates the simulation everystep, it goes through and
		 * applies the forces to every planet and deaks with collisions
		 */
		
		for (int i = 0; i < planets.size(); i++) {
			for (int k = i + 1; k < planets.size(); k++) {
				Planet p1 = planets.get(i);
				Planet p2 = planets.get(k);
				if (p1 != p2) {
					// if they arent the same planet apply the gravity of p2 to p1
					// A = G(p2.mass / distance^2) * direction
					// get the distance vector 
					Vector position = p2.getPosition();
					position.sub(p1.getPosition());
					// get the distance between the planets and direction
					double distance = position.magnitude();
					Vector scale = position.normalize();
					// do the equation
					double temp = G * (p2.getMass() / ((distance * distance) + (softening * softening)));
					scale.scale(temp);
					// add to each planets force
					p1.addForce(scale);
					scale.scale(-1);
					p2.addForce(scale);
				}
			}
		}
		
		// now apply every force to every planet
		for (Planet p : planets) {
			p.integrate(step);
		}
		
		// planets to be removed
		ArrayList<Planet> removed = new ArrayList<>();
		// handle collsions
		for (int i = 0; i < planets.size(); i++) {
			for (int k = i + 1; k < planets.size(); k++) {
				Planet p1 = planets.get(i);
				Planet p2 = planets.get(k);
				if (p1 != p2) {
					// get the distance between the planets
					Vector pos = p1.getPosition();
					pos.sub(p2.getPosition());
					double distance = pos.magnitude();
					// if distance < p1.radius + p2.radius
					if (distance < p1.getRadius() + p2.getRadius()) {
						// apply the collision
						collision(p1, p2);
						// print that a collision happened
						System.out.println("COLLISION! between planets: " + p1.getId() + " and " + p2.getId());
						// add planets to list of to be removed
						removed.add(p2);
					}
				}
			}
		}
		// remove planets
		this.planets.removeAll(removed);
	}
	
	private void collision(Planet p1, Planet p2) {
		/*
		 * This Function takes in two planet objects and deals with a collision between the two;
		 * they become one planet with combined mass and an accurate velocity result
		 */
		
		// get the combined mass
		double mass = p1.getMass() + p2.getMass();
		double p1Mass = p1.getMass();
		double p2Mass = p2.getMass();
		// get the accurate velocities for x,y,z
		// newVelocity = (v1m1 + v2m2) / m1 + m2
		Vector newVelocity = p1.getVelocity();
		newVelocity.scale(p1Mass);
		Vector p2Velocity = p2.getVelocity();
		p2Velocity.scale(p2Mass);
		newVelocity.add(p2Velocity);
		newVelocity.divide(mass);
		// update the position of p1 to be the center of mass between it and p2
		// newPos = (v1m1 + v2m2) / m1 + m2
		Vector newPosition = p1.getPosition();
		newPosition.scale(p1Mass);
		Vector p2Pos = p2.getPosition();
		p2Pos.scale(p2Mass);
		newPosition.add(p2Pos);
		newPosition.divide(mass);
		// update p1's vals
		p1.setMass(mass);
		p1.setVelocity(newVelocity);
		p1.setPosition(newPosition);
	}
}