package hw1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Lawson Port
 *
 */
public class Town {

	private int length, width; // Row and col (first and second indices)
	public TownCell[][] grid;

	/**
	 * Constructor to be used when user wants to generate grid randomly, with the
	 * given seed. This constructor does not populate each cell of the grid (but
	 * should assign a 2D array to it).
	 * 
	 * @param length
	 * @param width
	 */
	public Town(int length, int width) {
		// assigns a 2D array of type TownCell to the grid
		grid = new TownCell[length][width];
		this.length = length;
		this.width = width;
	}

	/**
	 * Constructor to be used when user wants to populate grid based on a file.
	 * Please see that it simple throws FileNotFoundException exception instead of
	 * catching it. Ensure that you close any resources (like file or scanner) which
	 * is opened in this function.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 */
	public Town(String inputFileName) throws FileNotFoundException {
		File file = new File(inputFileName);
		Scanner scnr = new Scanner(file);

		// get number of rows and columns
		length = scnr.nextInt();
		width = scnr.nextInt();

		// assign array to the grid
		grid = new TownCell[length][width];

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				String letter = scnr.next();

				if (letter.equals("C") || letter.equals("c")) {
					// Casual
					grid[i][j] = new Casual(this, i, j);
				} else if (letter.equals("E") || letter.equals("e")) {
					// Empty
					grid[i][j] = new Empty(this, i, j);
				} else if (letter.equals("O") || letter.equals("o")) {
					// Outage
					grid[i][j] = new Outage(this, i, j);
				} else if (letter.equals("R") || letter.equals("r")) {
					// Reseller
					grid[i][j] = new Reseller(this, i, j);
				} else {
					// streamer
					grid[i][j] = new Streamer(this, i, j);
				}
			}
		}
		scnr.close();
	}

	/**
	 * Returns width of the grid.
	 * 
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns length of the grid.
	 * 
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Initialize the grid by randomly assigning cell with one of the following
	 * class object: Casual, Empty, Outage, Reseller OR Streamer
	 */
	public void randomInit(int seed) {
		Random rand = new Random(seed);

		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				int randNum = rand.nextInt(5);

				if (randNum == 0) {
					// Casual
					grid[i][j] = new Casual(this, i, j);
				} else if (randNum == 1) {
					// Empty
					grid[i][j] = new Empty(this, i, j);
				} else if (randNum == 2) {
					// Outage
					grid[i][j] = new Outage(this, i, j);
				} else if (randNum == 3) {
					// Reseller
					grid[i][j] = new Reseller(this, i, j);
				} else {
					// streamer
					grid[i][j] = new Streamer(this, i, j);
				}
			}
		}
	}

	/**
	 * Output the town grid. For each square, output the first letter of the cell
	 * type. Each letter should be separated either by a single space or a tab. And
	 * each row should be in a new line. There should not be any extra line between
	 * the rows.
	 */
	@Override
	public String toString() {
		String s = "";
		StringBuilder sb = new StringBuilder(s);

		// loops to iterate through the grid and add to the string
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < width; j++) {
				if (this.grid[i][j].who() == State.CASUAL) {
					// Casual
					sb.append("C");
				} else if (this.grid[i][j].who() == State.EMPTY) {
					// Empty
					sb.append("E");
				} else if (this.grid[i][j].who() == State.OUTAGE) {
					// Outage
					sb.append("O");
				} else if (this.grid[i][j].who() == State.RESELLER) {
					// Reseller
					sb.append("R");
				} else {
					// streamer
					sb.append("S");
				}
				if (j < width - 1) {
					sb.append(" ");
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
