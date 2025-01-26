package hw1;

public class Outage extends TownCell {

	public Outage(Town p, int r, int c) {
		super(p, r, c);
	}

	@Override
	public State who() {
		return State.OUTAGE;
	}

	@Override
	public TownCell next(Town tNew) {
		// take census to determine neighbors
		super.census(nCensus);

		// outage becomes empty automatically
		Empty emptyCell = new Empty(tNew, row, col);
		return emptyCell;
	}
}
