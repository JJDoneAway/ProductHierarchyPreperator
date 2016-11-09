package de.hoehne.myfirsttest.algorithm;

public class SortMeSimple {

	
	public static void main(String[] args) {
				
		
		Integer[][] w = new Integer[10][2];
		
		//at first lets add some random stuff
		for (int i = 0; i < w.length; i++) {
			w[i][0] = i;
			w[i][1] = (int)(Math.random()*100);
			System.out.println(w[i][0] + " ==> " + w[i][1]);
		}
		
		System.out.println("-------------------------");
		System.out.println("-------------------------");
		
		for(int i = 0; i < w.length-1; i++){
			int x = w[i][1];
			
			for(int j = i+1; j < w.length; j++){
				int y = w[j][1];
				
				//Fliping
				if(y < x){
					int temp = w[i][0];
					w[i][0] = w[j][0];
					w[j][0] = temp;
					
					w[i][1] = y;
					w[j][1] = x;
					
					x = w[i][1];
					y = w[j][1];							
					
				}
				
			}
			
			
		}
		
		System.out.println("|counter   |value     |");
		System.out.println("|----------+----------|");

		for (Integer[] set : w) {
			System.out.printf("|%10d|%10d|\n", set[0], set[1]);
		}
		System.out.println("|----------+----------|");


	}
	
	
}
