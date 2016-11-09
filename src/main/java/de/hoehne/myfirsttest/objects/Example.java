package de.hoehne.myfirsttest.objects;

import java.util.Arrays;

public class Example {

	
	private static String[] myStrings = new String[]{"a", "b", "c"};	
	
	
	public static void main(String[] args) {
		for (String wert : myStrings) {
			System.out.println(wert);
		}
		
		
		for (int i = 0; i < myStrings.length; i++) {
			System.out.println(myStrings[i]);
		}
	}
	
}
