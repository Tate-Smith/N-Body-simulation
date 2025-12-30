/*
 * Purpose: This Class stores all the info for every planet in the simulation 
 */

public class Planet {
	private Vector position;
	private double mass;
	private static int count = 1;
	private int id;
	private Vector velocity;
	
	public Planet(Vector position, double mass, Vector velocity) {
		this.position = new Vector(position);
		this.mass = mass;
		this.id = count;
		count++;
		this.velocity = new Vector(velocity);
	}
	
	public Vector getPosition() {
		return position;
	}
	
	public Vector getVelocity() {
		return velocity;
	}

	public double getMass() {
		return mass;
	}
	
	public void setPosition(Vector v) {
		this.position = v;
	}
	
	public void setVelocity(Vector v) {
		this.velocity = v;
	}
	
	public String toString() {
		return "ID: " + id + "; location: " + position.getX() + ", " + position.getY() + ", Velocity: [" + velocity.getX() + ", " + velocity.getY() + "]";
	}
}