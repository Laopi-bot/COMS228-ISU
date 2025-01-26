package hw1;

public class Reseller extends TownCell {

	public Reseller(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.RESELLER;
	}

	@Override
	public TownCell next(Town tNew) {
		// take census to determine neighbors
		super.census(nCensus);

		// determine course of action from census results
		if (nCensus[CASUAL] <= 3) {
			// change state to EMPTY
			Empty emptyCell = new Empty(tNew, row, col);
			return emptyCell;
		} else if (nCensus[EMPTY] >= 3) {
			// change state to EMPTY
			Empty emptyCell = new Empty(tNew, row, col);
			return emptyCell;
		} else if (nCensus[CASUAL] >= 5) {
			// change to streamer
			Streamer myStreamer = new Streamer(tNew, row, col);
			return myStreamer;
		} else {
			// stay the same
			Reseller resell = new Reseller(tNew, row, col);
			return resell;
		}
	}
}
