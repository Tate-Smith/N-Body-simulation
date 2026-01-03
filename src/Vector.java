/*
 * Purpose: This is the vector class which stores the data x, y representing a vector
 */

public class Vector {
	private double x;
	private double y;
	private double z;
	
	public Vector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(Vector v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
	public void add(Vector other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
	}
	
	public void sub(Vector other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
	}
	
	public void scale(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
	}
	
	public void divide(double scalar) {
		this.x = this.x / scalar;
		this.y = this.y / scalar;
		this.z = this.z / scalar;
	}
	
	public double magnitude() {
		return Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
	}
	
	public Vector normalize() {
		return new Vector(this.x / this.magnitude(), this.y / this.magnitude(), this.z / this.magnitude());
	}
	
	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
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
	
	public String toString() {
		return String.format("%.4f,%.4f,%.4f", x, y, z);
	}
}
