package hw1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class TownTest {

	@Test
	void testTownIntInt() {
		Town testTown = new Town(4, 4);

		// make sure towncell is assigned to grid properly
		// and other tests
		assertEquals(null, testTown.grid[2][3]);
		assertEquals(4, testTown.getLength());
		assertEquals(4, testTown.getWidth());
	}

	@Test
	void testTownString() throws FileNotFoundException {

		Town tNew = new Town("grid.txt");

		// make sure all the cells are created from the file properly
		assertEquals(State.OUTAGE, tNew.grid[0][0].who());
		assertEquals(State.RESELLER, tNew.grid[0][1].who());
		assertEquals(State.OUTAGE, tNew.grid[0][2].who());
		assertEquals(State.RESELLER, tNew.grid[0][3].who());
		assertEquals(State.EMPTY, tNew.grid[1][0].who());
		assertEquals(State.EMPTY, tNew.grid[1][1].who());
		assertEquals(State.CASUAL, tNew.grid[1][2].who());
		assertEquals(State.OUTAGE, tNew.grid[1][3].who());
		assertEquals(State.EMPTY, tNew.grid[2][0].who());
		assertEquals(State.STREAMER, tNew.grid[2][1].who());
		assertEquals(State.OUTAGE, tNew.grid[2][2].who());
		assertEquals(State.STREAMER, tNew.grid[2][3].who());
		assertEquals(State.EMPTY, tNew.grid[3][0].who());
		assertEquals(State.OUTAGE, tNew.grid[3][1].who());
		assertEquals(State.RESELLER, tNew.grid[3][2].who());
		assertEquals(State.RESELLER, tNew.grid[3][3].who());
	}

	@Test
	void testGetWidth() {
		Town testTown = new Town(2, 4);
		assertEquals(4, testTown.getWidth());
	}

	@Test
	void testGetLength() {
		Town testTown = new Town(2, 4);
		assertEquals(2, testTown.getLength());
	}

	@Test
	void testRandomInit() {
		Town tNew = new Town(2, 2);
		tNew.randomInit(123);
		// make sure that the method assigned the grid to a type
		assertNotEquals(null, tNew.grid[0][0].who());
		assertNotEquals(null, tNew.grid[0][1].who());
		assertNotEquals(null, tNew.grid[1][0].who());
		assertNotEquals(null, tNew.grid[1][1].who());
	}

	@Test
	void testToString() {
		Town town = new Town(2, 2);
		town.grid[0][0] = new Outage(town, 0, 0);
		town.grid[0][1] = new Reseller(town, 0, 1);
		town.grid[1][0] = new Outage(town, 1, 0);
		town.grid[1][1] = new Reseller(town, 1, 1);

		String townString = town.toString();
		Scanner scnr = new Scanner(townString);

		assertEquals("O", scnr.next());
		assertEquals("R", scnr.next());
		assertEquals("O", scnr.next());
		assertEquals("R", scnr.next());
		scnr.close();
	}

}
