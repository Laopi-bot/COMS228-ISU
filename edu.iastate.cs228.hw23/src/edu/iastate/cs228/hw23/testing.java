package edu.iastate.cs228.hw23;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class testing {
	
	public static void main(String[] args) {
		
		Point[] points = new Point[17];
		points[0] = new Point(0, 0);
		points[1] = new Point(-3, 0);
		points[2] = new Point(0, 0);
		points[3] = new Point(8, 0);
		points[4] = new Point(3, 0);
		points[5] = new Point(-6, 0);
		points[6] = new Point(-2, 0);
		points[7] = new Point(10, 0);
		points[8] = new Point(-7, 0);
		points[9] = new Point(5, 0);
		points[10] = new Point(7, 0);
		points[11] = new Point(10, 0);
		points[12] = new Point(-7, 0);
		points[13] = new Point(0, 0);
		points[14] = new Point(-1, 0);
		points[15] = new Point(-10, 0);
		points[16] = new Point(5, 0);
		
		SelectionSorter select = new SelectionSorter(points);
		select.sort();
		
		for(int i = 0; i < points.length; i++) {
			System.out.println(points[i].getX() + " ");
		}
		
	}

}
