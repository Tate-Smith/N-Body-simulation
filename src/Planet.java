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
		// increment count to have a new id for each planet
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
	
	public Vector getForce() {
		return new Vector(force);
	}
	
	public void addForce(Vector force) {
		this.force.add(force);
	}
	
	public void integrateVelocity(double step) {
		/*
		 * This function is used to integrate the planets Velocity at each
		 * step
		 */
		
		// A = F / M
		Vector a = new Vector(force);
		a.divide(mass);
				
		// Velocity = Velocity + A * step
		a.scale(step);
		this.velocity.add(a);
	}
	
	public void integratePosition(double step) {
		/*
		 * This function is used to integrate the planets Position at each
		 * step
		 */
		
		// Position = Position + Velocity * step
		Vector v = new Vector(velocity);
		v.scale(step);
		this.position.add(v);
	}
	
	public void clear() {
		force.set(0.0, 0.0, 0.0);
	}
	
	public void setMass(double mass) {
		this.mass = mass;
	}
	
	public void setPosition(Vector v) {
		this.position = new Vector(v);
	}
	
	public void setVelocity(Vector v) {
		this.velocity = new Vector(v);
	}
	
	public static void resetCount() {
		count = 1;
	}
}