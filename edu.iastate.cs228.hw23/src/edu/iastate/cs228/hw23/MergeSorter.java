package edu.iastate.cs228.hw23;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.lang.IllegalArgumentException;
import java.util.InputMismatchException;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.
 *
 */

public class MergeSorter extends AbstractSorter {
	// Other private instance variables if needed

	/**
	 * Constructor takes an array of points. It invokes the superclass constructor,
	 * and also set the instance variables algorithm in the superclass.
	 * 
	 * @param pts input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		algorithm = "Merge Sort";
	}

	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter.
	 * 
	 */
	@Override
	public void sort() {
		if (pointComparator != null) {
			mergeSortRec(points);
		}
	}

	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of
	 * points. One way is to make copies of the two halves of pts[], recursively
	 * call mergeSort on them, and merge the two sorted subarrays into pts[].
	 * 
	 * @param pts point array
	 */
	private void mergeSortRec(Point[] pts) {
		int length = pts.length;

		// base case
		if (length <= 1)
			return;

		int mid = length / 2;
		Point[] leftPts = new Point[mid];
		Point[] rightPts = new Point[length - mid];

		int j = 0;

		for (int i = 0; i < length; i++) {
			if (i < mid) {
				leftPts[i] = pts[i];
			} else {
				rightPts[j] = pts[i];
				j++;
			}
		}
		mergeSortRec(leftPts);
		mergeSortRec(rightPts);
		merge(leftPts, rightPts, pts);
	}

	/**
	 * Merges the two arrays together
	 * 
	 * @param pts
	 * @param left
	 * @param mid
	 * @param right
	 */
	private void merge(Point[] leftPts, Point[] rightPts, Point[] pts) {
		int leftLength = pts.length / 2;
		int rightLength = pts.length - leftLength;

		int i = 0, r = 0, l = 0;

		while (l < leftLength && r < rightLength) {
			if (pointComparator.compare(leftPts[l], rightPts[r]) < 0) {
				pts[i] = leftPts[l];
				l++;
				i++;
			} else {
				pts[i] = rightPts[r];
				r++;
				i++;
			}
		}
		while (l < leftLength) {
			pts[i] = leftPts[l];
			i++;
			l++;
		}
		while (r < rightLength) {
			pts[i] = rightPts[r];
			i++;
			r++;
		}
	}
}
