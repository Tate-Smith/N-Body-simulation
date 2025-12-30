/*
 * Purpose: This Class stores all the info for every planet in the simulation 
 */

public class Planet {
	private Vector position;
	private double mass;
	private static int count = 1;
	private int id;
	private Vector velocity;
	private double radius;
	private Vector force;
	
	public Planet(Vector position, double mass, Vector velocity, double radius) {
		this.position = new Vector(position);
		this.mass = mass;
		this.id = count;
		count++;
		this.velocity = new Vector(velocity);
		this.radius = radius;
		force = new Vector(0.0, 0.0, 0.0);
	}
	
	public Vector getPosition() {
		return new Vector(position);
	}
	
	public Vector getVelocity() {
		return new Vector(velocity);
	}

	public double getMass() {
		return mass;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public int getId() {
		return id;
	}
	
	public void addForce(Vector force) {
		this.force.add(force);
	}
	
	public void integrate(double step) {
		// A = F / M
		Vector a = new Vector(force);
		a.divide(mass);
		
		// Velocity = Velocity + A * step
		a.scale(step);
		this.velocity.add(a);
		
		// Position = Position + Velocity * step
		Vector v = new Vector(velocity);
		v.scale(step);
		this.position.add(v);
		
		// reset force 
		force.set(0.0, 0.0, 0.0);
		
	}
	
	public void setMass(double mass) {
		this.mass = mass;
	}
	
	public void setPosition(Vector v) {
		this.position = v;
	}
	
	public void setVelocity(Vector v) {
		this.velocity = v;
	}
	
	public String toString() {
		return String.format("ID: %d; location: %s, Velocity: %s", this.id, 
				this.position, this.velocity);
	}
}