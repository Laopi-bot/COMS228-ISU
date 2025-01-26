package hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TownCellTest {

	@Test
	void testTownCell() {
		Town testTown = new Town(2, 2);
		Streamer testStreamer = new Streamer(testTown, 0, 1);
		assertEquals(testTown, testStreamer.plain);
		assertEquals(0, testStreamer.row);
		assertEquals(1, testStreamer.col);
	}

	@Test
	void testCensus() {

		final int RESELLER = 0;
		final int EMPTY = 1;
		final int CASUAL = 2;
		final int OUTAGE = 3;
		final int STREAMER = 4;

		final int NUM_CELL_TYPE = 5;

		// Use this static array to take census.
		final int[] nCensus = new int[NUM_CELL_TYPE];
		Town town = new Town(4, 4);
		town.grid[0][0] = new Outage(town, 0, 0);
		town.grid[0][1] = new Reseller(town, 0, 1);
		town.grid[0][2] = new Outage(town, 0, 2);
		town.grid[0][3] = new Reseller(town, 0, 3);

		town.grid[1][0] = new Empty(town, 1, 0);
		town.grid[1][1] = new Empty(town, 1, 1);
		town.grid[1][2] = new Casual(town, 1, 2);
		town.grid[1][3] = new Outage(town, 1, 3);

		town.grid[2][0] = new Empty(town, 2, 0);
		town.grid[2][1] = new Streamer(town, 2, 1);
		town.grid[2][2] = new Outage(town, 2, 2);
		town.grid[2][3] = new Streamer(town, 2, 3);

		town.grid[3][0] = new Empty(town, 3, 0);
		town.grid[3][1] = new Outage(town, 3, 1);
		town.grid[3][2] = new Reseller(town, 3, 2);
		town.grid[3][3] = new Reseller(town, 3, 3);

		town.grid[3][1].census(nCensus);

		assertEquals(1, nCensus[0]);
		assertEquals(2, nCensus[1]);
		assertEquals(0, nCensus[2]);
		assertEquals(1, nCensus[3]);
		assertEquals(1, nCensus[4]);
	}

}
