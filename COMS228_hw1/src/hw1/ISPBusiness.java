package hw1;

import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Lawson Port
 *
 *         The ISPBusiness class performs simulation over a grid plain with
 *         cells occupied by different TownCell types.
 *
 */
public class ISPBusiness {

	/**
	 * Returns a new Town object with updated grid value for next billing cycle.
	 * 
	 * @param tOld: old/current Town object.
	 * @return: New town object.
	 */
	public static Town updatePlain(Town tOld) {
		Town tNew = new Town(tOld.getLength(), tOld.getWidth());

		for (int i = 0; i < tOld.getLength(); i++) {
			for (int j = 0; j < tOld.getWidth(); j++) {
				tNew.grid[i][j] = tOld.grid[i][j].next(tNew);
			}
		}
		return tNew;
	}

	/**
	 * Returns the profit for the current state in the town grid.
	 * 
	 * @param town
	 * @return
	 */
	public static int getProfit(Town town) {
		int casualCustomers = 0;
		for (int i = 0; i < town.getLength(); i++) {
			for (int j = 0; j < town.getWidth(); j++) {
				if (town.grid[i][j].who() == State.CASUAL) {
					casualCustomers++;
				}
			}
		}
		return casualCustomers;
	}

	/**
	 * Main method. Interact with the user and ask if user wants to specify elements
	 * of grid via an input file (option: 1) or wants to generate it randomly
	 * (option: 2).
	 * 
	 * Depending on the user choice, create the Town object using respective
	 * constructor and if user choice is to populate it randomly, then populate the
	 * grid here.
	 * 
	 * Finally: For 12 billing cycle calculate the profit and update town object
	 * (for each cycle). Print the final profit in terms of %. You should print the
	 * profit percentage with two digits after the decimal point: Example if profit
	 * is 35.5600004, your output should be:
	 *
	 * 35.56%
	 * 
	 * Note that this method does not throw any exception, so you need to handle all
	 * the exceptions in it.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		Town town = null;
		double totalProfit = 0;
		System.out.println("How would you like to populate the grid? (type 1 or type 2):"
				+ " 1: from a file. 2: randomly with seed");

		int input = scnr.nextInt();

		if (input == 1) {
			System.out.println("Please enter file path:");
			String filePath = scnr.next();
			try {
				town = new Town(filePath);
			} catch (FileNotFoundException e) {
				System.out.println("FIle not found!");
				e.printStackTrace();
			}

		} else if (input == 2) {
			System.out.println("Provide rows, columns, and seed integer separated by spaces:");
			int rows = scnr.nextInt();
			int columns = scnr.nextInt();
			int seed = scnr.nextInt();
			// create town object and fill it with random cells
			town = new Town(rows, columns);
			town.randomInit(seed);
		}

		// simulate the billing cycles
		for (int i = 0; i < 12; i++) {
			totalProfit += getProfit(town);
			// don't update cells on the last iteration
			if (i != 11) {
				town = updatePlain(town);
			}
		}

		double profitPercentage = 100 * ((totalProfit / 12) / (town.getLength() * town.getWidth()));
		System.out.println();
		System.out.printf("%.2f%%", profitPercentage);

		scnr.close();
	}
}
