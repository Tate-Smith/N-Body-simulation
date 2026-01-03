/*
 * Purpose: This class implements an octree to make the calculations faster for the simulation
 */

import java.util.ArrayList;

public class OctreeNode {
	private Vector center;
	private double width;
	private double length;
	private double height;
	private Vector centerOfMass;
	private double totalMass;
	private OctreeNode[] children;
	private ArrayList<Planet> planets;
	
	public OctreeNode(Vector center, double widthX, double lengthZ, double heightY) {
		this.width = widthX;
		this.length = lengthZ;
		this.height = heightY;
		this.center = new Vector(center);
		this.centerOfMass = new Vector(0.0, 0.0, 0.0);
		this.totalMass = 0.0;
		this.children = new OctreeNode[8];
		this.planets = new ArrayList<Planet>();;
	}
	
	public boolean isLeaf() {
		return children[0] == null;
	}
	
	public Vector getCenterOfMass() {
		return new Vector(centerOfMass);
	}
	
	public double getTotalMass() {
		return totalMass;
	}
	
	public ArrayList<Planet> getPlanets() {
		return new ArrayList<>(planets);
	}
	
	public OctreeNode[] getChildren() {
		return children;
	}
	
	public double getSize() {
		return Math.max(width, Math.max(height, length));
	}
	
	private void addPlanet(OctreeNode node, Planet planet) {
		/*
		 * This function takes in a OctreeNode and a Planet object and recurses through 
		 * the tree to insert the planet in the proper location
		 */
		
		// do center of mass calcs for the planet
		Vector position = planet.getPosition();
					
		node.centerOfMass.scale(node.totalMass);
		position.scale(planet.getMass());
		node.centerOfMass.add(position);
		node.totalMass += planet.getMass();
		node.centerOfMass.divide((node.totalMass));
		
		if (node.isLeaf()) {
			// if it is a leaf node then the planet goes here and then intialize its children
			node.planets.add(planet);
			
			// if there are over 4 planets in a node divide
			if (node.planets.size() == 4) node.divide();
		}
		else {
			// if not then recurse into the proper child based on the planets location
			int index = 0;
			Vector pos = planet.getPosition();
			// find which child to recurse into
			if (pos.getX() > node.center.getX()) index |= 1;
			if (pos.getY() > node.center.getY()) index |= 2;
			if (pos.getZ() > node.center.getZ()) index |= 4;
			addPlanet(node.children[index], planet);
		}
	}
	
	public void buildTree(ArrayList<Planet> planets, OctreeNode root) {
		/*
		 * This function takes in a list of planets and recursively builds an 
		 * octree from them, with only one planet per node and each node having eight
		 * childen
		 */
		
		for (Planet p : planets) {
			addPlanet(root, p);
		}
	}
	
	private void divide() {
		/*
		 * This function takes the octree and node and creates its eight children
		 */
		
		// get all the data to do the calculations for each nodes center
		double width = this.width / 2;
		double length = this.length / 2;
		double height = this.height / 2;
		double halfWidth = this.width / 4;
		double halfLength = this.length / 4;
		double halfHeight = this.height / 4;
		double x = center.getX();
		double y = center.getY();
		double z = center.getZ();
		
		// create each of the 8 children nodes then add them to this nodes children
		OctreeNode bottomNorthWest = new OctreeNode(new Vector(x - halfWidth, y - halfHeight, z + halfLength), width, length, height);
		this.children[0] = bottomNorthWest;
		
		OctreeNode bottomNorthEast = new OctreeNode(new Vector(x + halfWidth, y - halfHeight, z + halfLength), width, length, height);
		this.children[1] = bottomNorthEast;
		
		OctreeNode bottomSouthWest = new OctreeNode(new Vector(x - halfWidth, y - halfHeight, z - halfLength), width, length, height);
		this.children[2] = bottomSouthWest;

		OctreeNode bottomSouthEast = new OctreeNode(new Vector(x + halfWidth, y - halfHeight, z - halfLength), width, length, height);
		this.children[3] = bottomSouthEast;

		OctreeNode topNorthWest = new OctreeNode(new Vector(x - halfWidth, y + halfHeight, z + halfLength), width, length, height);
		this.children[4] = topNorthWest;

		OctreeNode topNorthEast = new OctreeNode(new Vector(x + halfWidth, y + halfHeight, z + halfLength), width, length, height);
		this.children[5] = topNorthEast;

		OctreeNode topSouthWest = new OctreeNode(new Vector(x - halfWidth, y + halfHeight, z - halfLength), width, length, height);
		this.children[6] = topSouthWest;

		OctreeNode topSouthEast = new OctreeNode(new Vector(x + halfWidth, y + halfHeight, z - halfLength), width, length, height);
		this.children[7] = topSouthEast;
		
		// redistribute the planets
		for (Planet p : this.planets) addPlanet(this, p);
		this.planets.clear();
		
	}
	
	public void calculateForce(Planet p, double G, double softening) {
		/*
		 * This method takes in a Planet the g force and the softening and calculates the force on this planet
		 * using the octree
		 */
		
		if (this.isLeaf()) {
			// if its a leaf then calculate all the forces from the planets in this leaf
			for (Planet leaf : this.planets) {
				if (leaf != p) {
					// if they arent the same then calculate the forces
					// F = G(p2.mass * p1.mass / distance^2) * direction
					// get the distance vector 
					Vector position = leaf.getPosition();
					position.sub(p.getPosition());
					// get the distance between the planets and direction
					double distance = position.magnitude();
					Vector scale = position.normalize();
					// do the equation
					double temp = G * ((p.getMass() * leaf.getMass()) / ((distance * distance) + (softening * softening)));
					scale.scale(temp);
					// add to each planets force
					p.addForce(scale);
				}
			}
		}
		else {
			// if not check if far enough for approximation
			Vector c = new Vector(this.centerOfMass);
			c.sub(p.getPosition());
			double dist = c.magnitude();
			
			if (dist != 0 && (this.getSize() / dist) < 0.5) {
				// if too far then use approximation for the  calculations
				Vector scale = c.normalize();
				// do the equation
				double temp = G * ((p.getMass() * this.totalMass)) / ((dist * dist) + (softening * softening));
				scale.scale(temp);
				// add to the planets force
				p.addForce(scale);
			}
			else {
				// if not too far then recurse into children
				for (OctreeNode child : this.children) {
					if (child != null) child.calculateForce(p, G, softening);
				}
			}
		}
	}
	
	public void checkNearby(Vector pos, double dist, ArrayList<Planet> close) {
		/*
		 * This method takes in a vector that is the position of a planet, a double distance from the planet,
		 * and an arrayList, it finds all planets within the distance of the current planet and adds them to
		 * the arrayList
		 */
		
		// find the distance from this planet and the center of the node
		Vector center = new Vector(this.center);
		center.sub(pos);
		double distToCenter = center.magnitude();
		
		// check if too far away
		if ((distToCenter - this.getSize()) > dist) return;
		
		// if its a leaf add all the planets to the list
		if (this.isLeaf()) close.addAll(this.planets);
		else {
			// if not recurse into the children
			for (OctreeNode child : this.children) {
				if (child != null) child.checkNearby(pos, dist, close); 
			}
		}
	}
}
