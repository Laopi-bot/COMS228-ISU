package hw1;

/**
 * 
 * @author lawson Port
 * 
 *         empty cell class extends the town cell class
 *
 */
public class Empty extends TownCell {

	public Empty(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.EMPTY;
	}

	@Override
	public TownCell next(Town tNew) {
		// take census to determine neighbors
		super.census(nCensus);

		if ((nCensus[EMPTY] + nCensus[OUTAGE]) <= 1) {
			// change to reseller
			Reseller resell = new Reseller(tNew, row, col);
			return resell;
		} else {
			// change to casual
			Casual casualCell = new Casual(tNew, row, col);
			return casualCell;
		}
	}

}
