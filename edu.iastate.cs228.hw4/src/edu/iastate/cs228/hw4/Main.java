package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * the brains of the decoding
 * Extracts data from file and interprets it and decodes it 
 * @author lawso
 *
 */
public class Main {
	public static void main(String[] args) throws IOException{
		System.out.println("Enter filename:");
		Scanner scnr = new Scanner(System.in);
		String filename = scnr.next();
		
		
		
		File f = new File(filename);
		scnr = new Scanner(f);
		
		//get strings for the encoding scheme and the binary
		String stuff = "";
		String encoding = "";
		String binary = "";
		int j = 0;
		
		while(scnr.hasNext()) {
			stuff = scnr.nextLine();
			if(stuff.indexOf('^') >= 0) {
				if(j > 0) {
					encoding += '\n';
				}
				encoding += stuff;
			}
			else {
				binary += stuff;
			}
			j++;
		}
		
		
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < encoding.length(); i++) {
			char ch = encoding.charAt(i);
			if(ch != '^') {
				sb.append(ch);
			}
		}
		String codes = sb.toString();
		
		//methods
		MsgTree root = new MsgTree(encoding);
		MsgTree.printCodes(root, codes);
		root.decode(root, binary);
		
		scnr.close();
	}
}
