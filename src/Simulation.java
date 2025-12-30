/*
 * Purpose: This is the simulation class that stores all the planets in the sim
 * and performs all the calculations on them
 */

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

public class Simulation {
	private double G;
	private double softening;
	private ArrayList<Planet> planets;
	private FileWriter writer;
	
	public Simulation(double G, double softening) throws IOException {
		this.G = G;
		this.softening = softening;
		planets = new ArrayList<>();
		// intialize the output file and add header
		writer = new FileWriter("SimulationOutput.csv");
		writer.write("STEP, ID, MASS, X,Y,Z, VELOCITY X, VELOCITY Y, VELOCITY Z, COLLISION?\n");
	}
	
	public void addPlanet(Planet p) {
		planets.add(p);
	}
	
	public void close() throws IOException {
		/*
		 * This function closes the writer 
		 */

		writer.close();
	}
	
	private void writeToFile(int step) throws IOException {
		/*
		 * This function prints to an output file the id, mass, position and velocity, of every
		 * planet before each update
		 */
		
		for (Planet p : planets) {
			writer.write(step + ", " + p.getId() + ", " + p.getMass() + ", " + p.getPosition() + ", " + p.getVelocity() + ",-" + "\n");
		}
	}
	
	public void update(int step) throws IOException {
		/*
		 * This function is what updates the simulation everystep, it goes through and
		 * applies the forces to every planet and deaks with collisions
		 */
		
		// if the step is divisible by 100 write to outputfile
		if (step % 100 == 0) writeToFile(step);
		
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
						// print to the output file that a collision happened
						this.writer.write(step + ", " + p1.getId() + ", " + p1.getMass() + ", " + 
						p1.getPosition() + ", " + p1.getVelocity() + "," + p1.getId() + " and " + p2.getId() + "\n");
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