package edu.iastate.cs228.hw2;

import java.io.File;



import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 *
 * 
 * @author Lawson Port
 * 
 * This class sorts all the points in an array of 2D points to determine a
 * reference point whose x and y coordinates are respectively the medians of the
 * x and y coordinates of the original points.
 * 
 * It records the employed sorting algorithm as well as the sorting time for
 * comparison.
 *
 */
public class PointScanner {
	private Point[] points;

	private Point medianCoordinatePoint; // point whose x and y coordinates are respectively the medians of
											// the x coordinates and y coordinates of those points in the array
											// points[].
	private Algorithm sortingAlgorithm;

	protected long scanTime; // execution time in nanoseconds.

	/**
	 * This constructor accepts an array of points and one of the four sorting
	 * algorithms as input. Copy the points into the array points[].
	 * 
	 * @param pts input array of points
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public PointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException {
		if (pts == null || pts.length == 0) {
			IllegalArgumentException e = new IllegalArgumentException("Pts == null or Pts == 0");
			throw e;
		}
		points = pts;
		sortingAlgorithm = algo;
	}

	/**
	 * This constructor reads points from a file.
	 * 
	 * @param inputFileName
	 * @throws FileNotFoundException
	 * @throws InputMismatchException if the input file contains an odd number of
	 *                                integers
	 */
	protected PointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException {
		ArrayList<Point> filePoints = new ArrayList<Point>();
		File file = new File(inputFileName);
		Scanner scnr = new Scanner(file);

		while (scnr.hasNextInt()) {
			int x = scnr.nextInt();
			int y;
			if (scnr.hasNextInt()) {
				y = scnr.nextInt();
			} else {
				y = 0;
				//close scanner if mismatch exception occurs
				scnr.close();
				throw new InputMismatchException();
			}
			filePoints.add(new Point(x, y));
		}
		
		sortingAlgorithm = algo;
		points = filePoints.toArray(new Point[0]);
		scnr.close();
	}

	/**
	 * Carry out two rounds of sorting using the algorithm designated by
	 * sortingAlgorithm as follows:
	 * 
	 * a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
	 * points[] again by the y-coordinate to get the median y-coordinate. c)
	 * Construct medianCoordinatePoint using the obtained median x- and
	 * y-coordinates.
	 * 
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter,
	 * InsertionSorter, MergeSorter, or QuickSorter to carry out sorting.
	 * 
	 * @param algo
	 * @return
	 */
	public void scan() {
		// reset value of scan time
		scanTime = 0;

		AbstractSorter aSorter;
		// create an object to be referenced by aSorter according to sortingAlgorithm.
		// for each of the two rounds of sorting
		if (sortingAlgorithm == Algorithm.SelectionSort) {
			aSorter = new SelectionSorter(points);
		} else if (sortingAlgorithm == Algorithm.InsertionSort) {
			aSorter = new InsertionSorter(points);
		} else if (sortingAlgorithm == Algorithm.MergeSort) {
			aSorter = new MergeSorter(points);
		} else {
			aSorter = new QuickSorter(points);
		}

		// a) Sort points[] by the x-coordinate to get the median x-coordinate. b) Sort
		// * points[] again by the y-coordinate to get the median y-coordinate. c)
		// * Construct medianCoordinatePoint using the obtained median x- and
		// * y-coordinates.

		// Get mean x coordinate
		aSorter.setComparator(0);
		// sorts and collects time
		long xStart = System.nanoTime();
		aSorter.sort();
		long xFinal = System.nanoTime() - xStart;
		// get mean x value for point creation
		int meanXvalue = aSorter.getMedian().getX();

		// get mean y coordinate
		aSorter.setComparator(1);
		// sorts and collects time
		long yStart = System.nanoTime();
		aSorter.sort();
		long yFinal = System.nanoTime() - yStart;
		int meanYvalue = aSorter.getMedian().getY();

		// update scanTime Variable with times
		scanTime = xFinal + yFinal;

		// update medianCoordinatePoint variable
		medianCoordinatePoint = new Point(meanXvalue, meanYvalue);
		
		//write it to a file for some reason
		//im sure for your (TA grading this) testing it will be annoying to change
		//the file name. Sorry about that lol
			try {
				writeMCPToFile("mcpt.txt");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		System.out.println(stats());
	}

	/**
	 * Outputs performance statistics in the format:
	 * 
	 * <sorting algorithm> <size> <time>
	 * 
	 * For instance,
	 * 
	 * selection sort 1000 9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description.
	 */
	public String stats() {
		//whitespace formatting for longer sorting method names
		if(sortingAlgorithm == Algorithm.SelectionSort || sortingAlgorithm == Algorithm.InsertionSort)
			return sortingAlgorithm + "   " + points.length + "  " + scanTime;
		//whitespace formatting for shorter sorting method names
		else
			return sortingAlgorithm + "       " + points.length + "  " + scanTime;
	}

	/**
	 * Write MCP after a call to scan(), in the format "MCP: (x, y)" The x and y
	 * coordinates of the point are displayed on the same line with exactly one
	 * blank space in between.
	 */
	@Override
	public String toString() {
		return "MCP: " + medianCoordinatePoint.toString();
	}

	/**
	 * 
	 * This method, called after scanning, writes point data into a file by
	 * outputFileName. The format of data in the file is the same as printed out
	 * from toString(). The file can help you verify the full correctness of a
	 * sorting result and debug the underlying algorithm.
	 * 
	 * ---------------------------------------------------------------------------
	 * ADDED OUTPUTFILENAME PARAMETER BASED OFF OF INSTRUCTOR RESPONSE IN PIAZZA
	 * ---------------------------------------------------------------------------
	 * 
	 * @throws FileNotFoundException
	 */
	public void writeMCPToFile(String outputFileName) throws FileNotFoundException {
		try {
			FileWriter myWriter = new FileWriter(outputFileName);
			myWriter.write(toString());
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occured when writing to file.");
			e.printStackTrace();
		}
	}

}
