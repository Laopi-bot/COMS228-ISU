package edu.iastate.cs228.hw23;

import java.io.FileNotFoundException;

import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.Comparator;
import java.util.InputMismatchException;

/**
 *  
 * @author Lawson Port
 *
 */

/**
 * 
 * This class implements selection sort.
 *
 */
public class SelectionSorter extends AbstractSorter {
	// Other private instance variables if you need ...

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts
	 */
	public SelectionSorter(Point[] pts) {
		super(pts);
		algorithm = "selection sort";
	}

	/**
	 * Apply selection sort on the array points[] of the parent class
	 * AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		//if setComparator has been called
		if (pointComparator != null) {
			for (int i = 0; i < points.length; i++) {
				int minIndex = 0;
				for (int j = i + 1; j < points.length; j++) {
					// if j point is less than min index, do stuff
					if (pointComparator.compare(points[j], points[minIndex]) < 0) {
						minIndex = j;
					}
				}
				this.swap(i, minIndex);
			}
		}
	}
}
