package hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ISPBusinessTest {

	@Test
	void testUpdatePlain() {
		// old town
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

		// create new town
		Town tNew = ISPBusiness.updatePlain(town);

		assertEquals(State.EMPTY, tNew.grid[0][0].who());
		assertEquals(State.EMPTY, tNew.grid[0][1].who());
		assertEquals(State.EMPTY, tNew.grid[0][2].who());
		assertEquals(State.EMPTY, tNew.grid[0][3].who());
		assertEquals(State.CASUAL, tNew.grid[1][0].who());
		assertEquals(State.CASUAL, tNew.grid[1][1].who());
		assertEquals(State.OUTAGE, tNew.grid[1][2].who());
		assertEquals(State.EMPTY, tNew.grid[1][3].who());
		assertEquals(State.CASUAL, tNew.grid[2][0].who());
		assertEquals(State.OUTAGE, tNew.grid[2][1].who());
		assertEquals(State.EMPTY, tNew.grid[2][2].who());
		assertEquals(State.OUTAGE, tNew.grid[2][3].who());
		assertEquals(State.CASUAL, tNew.grid[3][0].who());
		assertEquals(State.EMPTY, tNew.grid[3][1].who());
		assertEquals(State.EMPTY, tNew.grid[3][2].who());
		assertEquals(State.EMPTY, tNew.grid[3][3].who());

	}

	@Test
	void testGetProfit() {
		// old town
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

		assertEquals(1, ISPBusiness.getProfit(town));
	}
}
