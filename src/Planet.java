/*
 * This Class stores all the info for every planet in the simulation 
 */

public class Planet {
	private double x;
	private double y;
	private double z;
	private double mass;
	private static int count = 1;
	private int id;
	
	public Planet(double x, double y, double z, double mass) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.mass = mass;
		this.id = count;
		count++;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getMass() {
		return mass;
	}
	
	public String toString() {
		return "ID: " + id + "; location: " + x + ", " + y + ", " + z;
	}
}
