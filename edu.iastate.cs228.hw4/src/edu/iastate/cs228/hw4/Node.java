package edu.iastate.cs228.hw4;

/**
 * Simulates a node of the MessageTree
 * @author lawso
 *
 */
public class Node {

	char value;
	
	Node left;
	
	Node right;
	
	Node(char value){
		this.value = value;
		right = null;
		left = null;
	}
}
