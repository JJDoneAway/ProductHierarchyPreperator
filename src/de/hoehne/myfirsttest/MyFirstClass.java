package de.hoehne.myfirsttest;

public class MyFirstClass {

	public static void main(String[] args) {
		Double myDouble = new Double(5.0);
		double myDifferentDouble = 5.0;
		Double myTotalDifferentDouble = 6.0;
		
		System.out.println(myDouble);

		String myFormat = "|%15s|%15s|%15s|";

		String[][] myArguments = { { "a", "b", "c" }, { "µ", "ħ", "ĸ" } };
		System.out.printf(myFormat, myArguments[1]);

		System.out.println("\n----------------------------------------------");

		String myFormat2 = "|%15f|%15f|%15f|";

		Double[][] myArguments2 = { { 1.0, 2.0, 3.0 }, { 4.0, 5.3, 6.4 } };
		System.out.printf(myFormat2, myArguments2[0]);

		double[][] myArguments3 = { { 1.0, 2.0, 3.0 }, { 4.0, 5.3, 6.4 } };
		System.out.println(myArguments3[0]);

	}

}
