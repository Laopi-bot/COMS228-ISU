package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 *  
 * @author Lawson Port
 *
 */

/**
 * 
 * This class implements insertion sort.
 *
 */

public class InsertionSorter extends AbstractSorter {
	// Other private instance variables if you need ...

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public InsertionSorter(Point[] pts) {
		super(pts);
		algorithm = "insertion sort";
	}

	/**
	 * Perform insertion sort on the array points[] of the parent class
	 * AbstractSorter.
	 */
	@Override
	public void sort() {
		if (pointComparator != null) {
			Point key;
			int j;
			for (int i = 0; i < points.length; i++) {
				key = points[i];
				j = i - 1;

				// Move elements of arr[0..i-1] that are greater than temp
				// to on position ahead of their current position
				while (j >= 0 && pointComparator.compare(points[j], key) > 0) {
					this.swap(j + 1, j);
					j = j - 1;
				}
				points[j + 1] = key;
			}
		}
	}
}
