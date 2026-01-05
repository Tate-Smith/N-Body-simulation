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
		// if the scalar is 0 set all vals to 0 to avoid a divide by 0 error
		if (scalar == 0.0) {
			this.x = 0.0;
			this.y = 0.0;
			this.z = 0.0;
		}
		else {
			this.x = this.x / scalar;
			this.y = this.y / scalar;
			this.z = this.z / scalar;
		}
	}
	
	public double magnitude() {
		return Math.sqrt((this.x * this.x) + (this.y * this.y) + (this.z * this.z));
	}
	
	public Vector normalize() {
		// if the magnitude is 0, set return a 0 vector to avoid dividing by 0
		double mag = this.magnitude();
		if (mag == 0) return new Vector(0.0, 0.0, 0.0);
		return new Vector(this.x / mag, this.y / mag, this.z / mag);
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
