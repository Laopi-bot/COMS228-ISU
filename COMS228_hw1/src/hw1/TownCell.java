package hw1;

/**
 * 
 * @author Lawson Port
 * 
 *         mimics the standard town cell
 *
 */
public abstract class TownCell {

	protected Town plain;
	protected int row;
	protected int col;

	// constants to be used as indices.
	protected static final int RESELLER = 0;
	protected static final int EMPTY = 1;
	protected static final int CASUAL = 2;
	protected static final int OUTAGE = 3;
	protected static final int STREAMER = 4;

	public static final int NUM_CELL_TYPE = 5;

	// Use this static array to take census.
	public static final int[] nCensus = new int[NUM_CELL_TYPE];

	public TownCell(Town p, int r, int c) {
		plain = p;
		row = r;
		col = c;
	}

	/**
	 * Checks all neigborhood cell types in the neighborhood. Refer to homework pdf
	 * for neighbor definitions (all adjacent neighbors excluding the center cell).
	 * Use who() method to get who is present in the neighborhood
	 * 
	 * @param counts of all customers
	 */
	public void census(int nCensus[]) {
		// zero the counts of all customers
		nCensus[RESELLER] = 0;
		nCensus[EMPTY] = 0;
		nCensus[CASUAL] = 0;
		nCensus[OUTAGE] = 0;
		nCensus[STREAMER] = 0;

		// determine how many neighbors the given cell has
		for (int i = 0; i < 3; i++) {
			int yCoord = row - 1 + i;

			// make sure y coord isn't out of bounds of the array in the negative direction
			// and continue to the next iteration if it is
			if (yCoord < 0) {
				continue;
			}

			// make sure y coord isn't out of bounds of the array in the positive direction
			// exit from the loop if it is
			if (yCoord == plain.getLength()) {
				break;
			}

			for (int j = 0; j < 3; j++) {
				int xCoord = col - 1 + j;

				// make sure x coord isn't out of bounds of the array in the negative direction
				// and continue to the next iteration if it is
				if (xCoord < 0) {
					continue;
				}

				// make sure x coord isn't out of bounds of the array in the positive direction
				// exit from the loop if it is
				if (xCoord == plain.getWidth()) {
					break;
				}

				// don't count the given cell
				if (row == yCoord && col == xCoord) {
					continue;
				}

				// getting the type of customer and incrementing array accordingly
				State customer = plain.grid[yCoord][xCoord].who();

				if (customer == State.RESELLER) {
					nCensus[RESELLER]++;
				} else if (customer == State.EMPTY) {
					nCensus[EMPTY]++;
				} else if (customer == State.CASUAL) {
					nCensus[CASUAL]++;
				} else if (customer == State.OUTAGE) {
					nCensus[OUTAGE]++;
				} else {
					nCensus[STREAMER]++;
				}
			}
		}

	}

	/**
	 * Gets the identity of the cell.
	 * 
	 * @return State
	 */
	public abstract State who();

	/**
	 * Determines the cell type in the next cycle.
	 * 
	 * @param tNew: town of the next cycle
	 * @return TownCell
	 */
	public abstract TownCell next(Town tNew);

}
