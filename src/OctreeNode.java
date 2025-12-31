import java.util.ArrayList;

public class OctreeNode {
	private Vector center;
	private double width;
	private double length;
	private double height;
	private Vector centerOfMass;
	private double totalMass;
	private OctreeNode[] children;
	private Planet planet;
	
	public OctreeNode(Vector center, double widthX, double lengthZ, double heightY) {
		this.width = widthX;
		this.length = lengthZ;
		this.height = heightY;
		if (center == null) this.center = new Vector(0.0, 0.0, 0.0);
		else this.center = new Vector(center);
		this.centerOfMass = new Vector(0.0, 0.0, 0.0);
		this.totalMass = 0.0;
		this.children = new OctreeNode[8];
		this.planet = null;
	}
	
	public boolean isLeaf() {
		return children == null;
	}
	
	private void addPlanet(OctreeNode node, Planet planet) {
		/*
		 * This function takes in a OctreeNode and a Planet object and recurses through 
		 * the tree to insert the planet in the proper location
		 */
		
		if (node.isLeaf()) {
			// if it is a leaf node then the planet goes here and then intialize its children
			node.planet = planet;
			node.divide();
		}
		else {
			// if not then recurse into the proper child based on the planets location
			Vector position = planet.getPosition();
			// find which child to recurse into
			if (position.getY() >= node.center.getY()) {
				// if its in the top half of the space
				if (position.getX() >= node.center.getX()) {
					// if the planet is in the North of the top half
					if (position.getZ() >= node.center.getZ()) {
						// if the planet is in the topnortheast
						addPlanet(node.children[5], planet);
					}
					else {
						// if the planet is in the topnorthwest
						addPlanet(node.children[4], planet);
					}
				}
				else {
					// if the planet is in the south of the top half
					if (position.getZ() >= node.center.getZ()) {
						// if the planet is in the topsoutheast
						addPlanet(node.children[7], planet);
					}
					else {
						// if the planet is in the topsouthwest
						addPlanet(node.children[6], planet);
					}
				}
			}
			else {
				// if its in the bottom half of the space
				if (position.getX() >= node.center.getX()) {
					// if the planet is in the North of the bottom half
					if (position.getZ() >= node.center.getZ()) {
						// if the planet is in the bottomnortheast
						addPlanet(node.children[1], planet);
					}
					else {
						// if the planet is in the bottomnorthwest
						addPlanet(node.children[0], planet);
					}
				}
				else {
					// if the planet is in the south of the top half
					if (position.getZ() >= node.center.getZ()) {
						// if the planet is in the bottomsoutheast
						addPlanet(node.children[3], planet);
					}
					else {
						// if the planet is in the bottomsouthwest
						addPlanet(node.children[2], planet);
					}
				}
			}
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
		double halfLength = this.width / 4;
		double halfHeight = this.width / 4;
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

	}
}
