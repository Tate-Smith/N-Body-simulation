import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		// Take in input from the user to determine the size of the simulation space
		Scanner input = new Scanner(System.in);
		System.out.print("Dimensions of simulation space (width, height)");
		String info = input.nextLine();
		String[] coordinates = info.split(",");
		
		// also ask how many planets they want then those planets info
		
		
		input.close();
	}

}
