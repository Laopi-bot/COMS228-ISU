package edu.iastate.cs228.hw2;

import java.util.Comparator;


/**
 * 
 * @author Lawson Port
 * 
 * calls the first point in compare's compareTo Method and returns that 
 * method's value. Overrides Comparator interface's compare method
 *
 */
public class ComparingThings implements Comparator<Point> {

	@Override
	public int compare(Point o1, Point o2) {
		//calls compare to in o1 and compares it to o2

		//returns -1 if o1 < o2
		//returns 0 if o1 = o2
		//returns 1 if o1 > o2
		return o1.compareTo(o2);
	}

	
	
}
