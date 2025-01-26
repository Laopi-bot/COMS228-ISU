package hw1;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class OutageTest {

	@Test
	void testWho() {
		Town city = new Town(4, 4);
		Outage outageCell = new Outage(city, 1, 1);
		assertEquals(State.OUTAGE, outageCell.who());
	}

	@Test
	void testNext() {
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

		// new town
		Town tNew = new Town(4, 4);

		// test town
		Town testTown = new Town(4, 4);

		// create new outage in same spot as one to be created
		// for testing
		Empty testEmpty = new Empty(testTown, 0, 0);

		// call the next method
		tNew.grid[0][0] = town.grid[0][0].next(tNew);

		// can't compare the two objects directly, so we compare everything
		// about the object that makes it that specific object
		assertEquals(testEmpty.col, tNew.grid[0][0].col);
		assertEquals(testEmpty.row, tNew.grid[0][0].row);
		assertEquals(testEmpty.who(), tNew.grid[0][0].who());
	}

	@Test
	void testOutage() {
		Town testTown = new Town(4, 4);
		Outage testOutage = new Outage(testTown, 2, 3);

		// test casual fields
		assertEquals(2, testOutage.row);
		assertEquals(3, testOutage.col);
		assertEquals(testTown, testOutage.plain);
	}

}
