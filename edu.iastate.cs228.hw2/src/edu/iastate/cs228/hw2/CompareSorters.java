package edu.iastate.cs228.hw2;

/**
 *  
 * @author Lawson Port
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from
	 * files. Use them as coordinates to construct points. Scan these points with
	 * respect to their median coordinate point four times, each time using a
	 * different sorting algorithm.
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException {
		// start of method initializations
		PointScanner[] scanners = new PointScanner[4];
		Scanner scnr = new Scanner(System.in);
		System.out.println("Performances of Four Sorting Algorithms in Point Scanning");
		System.out.println();
		System.out.println("keys: 1(random integers)  2(file input)  3(exit)");

		int sorterInput = 0;
		int otherInput = 0;
		int i = 1;
		// main loop for the trials
		while (1 == 1) {
			// get input from user
			System.out.print("Trial " + i + ": ");
			sorterInput = scnr.nextInt();
			if (sorterInput == 1) {
				// random points
				Random rand = new Random();
				System.out.print("Enter number of random points: ");
				otherInput = scnr.nextInt();
				Point[] randomPoints = generateRandomPoints(otherInput, rand);

				// since we generated random points, don't use file name
				// and give the randomly generated array to the constructor
				scanners[0] = new PointScanner(randomPoints, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(randomPoints, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(randomPoints, Algorithm.MergeSort);
				scanners[3] = new PointScanner(randomPoints, Algorithm.QuickSort);
			} else if (sorterInput == 2) {
				// Points from file
				System.out.println("Points from a file");
				System.out.print("File Name: ");
				String fileName = scnr.next();

				// since we were given a file, initialize scanners with file name
				scanners[0] = new PointScanner(fileName, Algorithm.SelectionSort);
				scanners[1] = new PointScanner(fileName, Algorithm.InsertionSort);
				scanners[2] = new PointScanner(fileName, Algorithm.MergeSort);
				scanners[3] = new PointScanner(fileName, Algorithm.QuickSort);

			} else if (sorterInput == 3) {
				break;
			} else {
				System.out.println("Invalid Input!");
			}
			// format print statements
			System.out.println();
			System.out.println("algorithm   size  time(ns)");
			System.out.println("--------------------------------");

			// loop through the 4 scanners calling scan which prints the stats
			for (int j = 0; j < 4; j++) {
				scanners[j].scan();
			}

			// wrapping up print statements to prepare for the next loop iteration
			System.out.println("--------------------------------");
			System.out.println();
			i++;
		}
		scnr.close();
	}

	/**
	 * This method generates a given number of random points. The coordinates of
	 * these points are pseudo-random numbers within the range [-50,50] � [-50,50].
	 * Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing.
	 * 
	 * @param numPts number of points
	 * @param rand   Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException {
		if (numPts < 1) {
			throw new IllegalArgumentException();
		}
		Random generator = rand;
		Point[] points = new Point[numPts];

		for (int i = 0; i < numPts; i++) {
			int x = generator.nextInt(101) - 50;
			int y = generator.nextInt(101) - 50;
			points[i] = new Point(x, y);
		}
		return points;
	}

}
