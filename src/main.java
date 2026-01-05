/*
 * Last Update: 12/29/25
 * Purpose: This is the main class that parses the json file and sets up the simulation
 * along with running it
 */

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Random;

public class Main {
	public static void main(String[] args) {
		// take user input in as a JSON file from the command line
		// decode the JSON file to set up the physics sim
		JSONParser parser = new JSONParser();
		try {
			JSONObject root = (JSONObject) parser.parse(new FileReader(args[0]));
									
			// get all the simulation data
			JSONObject simulation = (JSONObject) root.get("simulation");
			double step = ((Number) simulation.get("step")).doubleValue();
			int numOfSteps = ((Number) simulation.get("steps")).intValue();
			double G = ((Number) simulation.get("G")).doubleValue();
			double softening = ((Number) simulation.get("softening")).doubleValue();
									
			Simulation sim = new Simulation(G, softening);
			
			// if the second input is "--gen" then it will be a procedurely generated sim
			if (args.length > 1  && args[1].toLowerCase().equals("--gen")) {
				// get the generation info
				JSONObject gen = (JSONObject) root.get("generation");
				
				int count = ((Number) gen.get("count")).intValue();
				JSONArray mass = (JSONArray) gen.get("massRange");
				JSONArray radius = (JSONArray) gen.get("radiusRange");
				JSONArray position = (JSONArray) gen.get("positionRange");
				JSONArray velocity = (JSONArray) gen.get("velocityRange");
				
				// for every planet to be created
				Random random = new Random();
				int k = 0;
				while (k < count) {
					// select a random mass in the range
					double m = random.nextDouble(((Number) mass.get(0)).doubleValue(), ((Number) mass.get(1)).doubleValue());
					// get a random radius in the range
					double r = random.nextDouble(((Number) radius.get(0)).doubleValue(), ((Number) radius.get(1)).doubleValue());
					// select a random x,y,z in the range for postion
					double x = random.nextDouble(((Number) position.get(0)).doubleValue(), ((Number) position.get(1)).doubleValue());
					double y = random.nextDouble(((Number) position.get(0)).doubleValue(), ((Number) position.get(1)).doubleValue());
					double z = random.nextDouble(((Number) position.get(0)).doubleValue(), ((Number) position.get(1)).doubleValue());
					// create a vector for position
					Vector pos = new Vector(x, y, z);
					// select a random x,y,z in the range for velocity
					x = random.nextDouble(((Number) velocity.get(0)).doubleValue(), ((Number) velocity.get(1)).doubleValue());
					y = random.nextDouble(((Number) velocity.get(0)).doubleValue(), ((Number) velocity.get(1)).doubleValue());
					z = random.nextDouble(((Number) velocity.get(0)).doubleValue(), ((Number) velocity.get(1)).doubleValue());
					// create a vector for velocity
					Vector v = new Vector(x, y, z);
					// create the planet and add it to the sim
					Planet p = new Planet(pos, m, v, r);
					sim .addPlanet(p);
					k++;
				}
			}
			else {
				// get all the planets and add them to the sim
				JSONArray planets = (JSONArray) root.get("planets");
				
				// for every planet listed create a planet object and add it to the sim
				for (Object obj : planets) {
					JSONObject planet = (JSONObject) obj;
					
					// get the mass, radius, position and velocity of every planet
					double mass = ((Number) planet.get("mass")).doubleValue();
					
					double radius = ((Number) planet.get("radius")).doubleValue();
					
					JSONArray position = (JSONArray) planet.get("position");
					Vector pos = new Vector(((Number) position.get(0)).doubleValue(), ((Number) position.get(1)).doubleValue(), ((Number) position.get(2)).doubleValue());
					
					JSONArray velocity = (JSONArray) planet.get("velocity");
					Vector v = new Vector(((Number) velocity.get(0)).doubleValue(), ((Number) velocity.get(1)).doubleValue(), ((Number) velocity.get(2)).doubleValue());
					
					// create every planet and add it to the sim
					Planet p = new Planet(pos, mass, v, radius);
					sim.addPlanet(p);
				}
				
			}
			// now simulate for n steps
			sim.initialize(step);
			int i = 0;
			while (i <= numOfSteps) {
				// call update on the simulation
				sim.update(step, i);
				i++;
			}
			sim.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}