package hw1;

public class Streamer extends TownCell {

	public Streamer(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.STREAMER;
	}

	@Override
	public TownCell next(Town tNew) {
		// take census to determine neighbors
		super.census(nCensus);

		// determine course of action from census results
		if ((nCensus[EMPTY] + nCensus[OUTAGE]) <= 1) {
			// change to reseller
			Reseller resell = new Reseller(tNew, row, col);
			return resell;
		} else if (nCensus[RESELLER] > 0) {
			// change state to OUTAGE
			Outage out = new Outage(tNew, row, col);
			return out;
		} else if (nCensus[OUTAGE] > 0) {
			// change state to EMPTY
			Empty emptyCell = new Empty(tNew, row, col);
			return emptyCell;
		} else if (nCensus[CASUAL] >= 5) {
			// change state to streamer
			Streamer myStreamer = new Streamer(tNew, row, col);
			return myStreamer;
		} else {
			// stay the same
			Streamer myStreamer = new Streamer(tNew, row, col);
			return myStreamer;
		}
	}
}
