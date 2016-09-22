package de.hoehne.myfirsttest;

public class MyFirstClass {

	public static void main(String[] args) {

		int steps = 10;

		Double[][] result = new Double[steps + 1][2];

		for (int i = 0; i <= steps; i++) {

			double x = (Math.PI / steps) * i - Math.PI / 2;

			double y = Math.sin(x);

			result[i][0] = x;
			result[i][1] = y;

		}

		System.out.println("|f(x)      |y         |");
		System.out.println("|----------+----------|");

		for (Double[] set : result) {
			System.out.printf("|%10.4f|%10.4f|\n", set[0], set[1]);
		}
		System.out.println("|----------+----------|");

	}

}
