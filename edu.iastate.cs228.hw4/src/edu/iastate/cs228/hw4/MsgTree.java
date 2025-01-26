package edu.iastate.cs228.hw4;

import java.util.Stack;

/**
 * 
 * @author Lawson Port
 *
 */
public class MsgTree {

	public char payloadChar;

	public MsgTree left;

	public MsgTree right;

	private static int staticCharIdx = 0;

	private static Node root = null;

	private static String keyCode;

	private static boolean print = false;

	public MsgTree(String encodingString) {
		// call recursive method
		treeRecursive(encodingString);
	}

	public MsgTree(char payloadChar) {

	}

	/**
	 * useing a node version of this instead of MsgTree
	 * 
	 * @param root
	 * @param code
	 */
	public static void printCodes(MsgTree root, String code) {
		betterPrintCodes(code);
	}

	private Node treeRecursive(String encodingString) {
		// recursive method
		Node root = new Node(encodingString.charAt(staticCharIdx));

		// update root instance variable for traversal later
		if (staticCharIdx == 0) {
			this.root = root;
		}

		staticCharIdx++;

		// base case, is not a ^
		if (encodingString.charAt(staticCharIdx - 1) != '^') {
			// we have a leaf
			return root;
		}

		root.left = treeRecursive(encodingString);
		root.right = treeRecursive(encodingString);

		return root;
	}

	private static void betterPrintCodes(String code) {
		print = true;
		Node starter = root;
		System.out.println("Character code\n--------------------");
		System.out.println(code);
		for (char key : code.toCharArray()) {
			keyCode = "";
			printNodeCodes(starter, key, keyCode);
		}
		print = false;
		System.out.println();
	}

	private static void printNodeCodes(Node root, char key, String keyCode) {

		// base case = it is character, print it's code
		if (root.value == key) {
			if (print) {
				System.out.println(" " + (root.value == '\n' ? "\\n" : root.value + " " ) + "  " + keyCode);
			}
			return;
		}
		// ties up loose ends
		if (root.value != '^' && root.value != key) {
			return;
		}

		printNodeCodes(root.left, key, keyCode + "0");
		printNodeCodes(root.right, key, keyCode + "1");
	}

	public void decode(MsgTree codes, String msg) {
		betterDecode(msg);
	}

	private void betterDecode(String msg) {
		Node current = root;
		Node starter = root;
		System.out.println("Message:");
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < msg.length(); i++) {
			char ch = msg.charAt(i);
			if(ch == '0') {
				current = current.left;
			}
			else {
				current = current.right;
			}
			
			if(current.value != '^') {
				printNodeCodes(starter, current.value , keyCode = "");
				sb.append(current.value);
				current = starter;
			}
		}
		System.out.println(sb.toString());
	}
}