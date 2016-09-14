package de.hoehne.myfirsttest;

public class MyFirstClass {

	public static void main(String[] args) {
		String myFormat = "|%15s|%15s|%15s|";

		String[][] myArguments = { { "a", "b", "c" }, { "µ", "ħ", "ĸ" } };
		System.out.printf(myFormat, myArguments[1]);

	}

}
