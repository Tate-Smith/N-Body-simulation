/*
 * Purpose: This is the vector class which stores the data x, y representing a vector
 */

public class Vector {
	private double x;
	private double y;
	
	public Vector(double x, double y ) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
	}
	
	public void add(Vector other) {
		this.x += other.x;
		this.y += other.y;
	}
	
	public void sub(Vector other) {
		this.x -= other.x;
		this.y -= other.y;
	}
	
	public void scale(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
	}
	
	public double magnitude() {
		return Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
	}
	
	public Vector normalize() {
		return new Vector(this.x / this.magnitude(), this.y / this.magnitude());
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
