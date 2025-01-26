package hw1;

/**
 * 
 * @author lawson Port
 * 
 *         acts as the Casual Class and extends TownCell
 *
 */
public class Casual extends TownCell {

	public Casual(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.CASUAL;
	}

	@Override
	public TownCell next(Town tNew) {
		// take census to determine neighbors
		super.census(nCensus);

		// determine course of action from census results
		// 6.a
		if ((nCensus[EMPTY] + nCensus[OUTAGE]) <= 1) {
			// change to reseller
			Reseller resell = new Reseller(tNew, row, col);
			return resell;
		} else if (nCensus[RESELLER] > 0) {
			// change state to OUTAGE
			Outage out = new Outage(tNew, row, col);
			return out;
		} else if (nCensus[STREAMER] > 0) {
			// change state to streamer
			Streamer myStreamer = new Streamer(tNew, row, col);
			return myStreamer;
		} else if (nCensus[CASUAL] >= 5) {
			// change state to streamer
			Streamer myStreamer = new Streamer(tNew, row, col);
			return myStreamer;
		} else {
			// if none of the other steps go through, keep same state
			Casual casualCell = new Casual(tNew, row, col);
			return casualCell;
		}
	}
}