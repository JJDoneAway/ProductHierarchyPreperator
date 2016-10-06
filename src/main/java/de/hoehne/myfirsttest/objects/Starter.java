package de.hoehne.myfirsttest.objects;

import java.awt.Color;

public class Starter {

	
	public static void main(String[] args) {
		Form meinKleinesDreieck = new Dreieck(1, 0.5);
		
		System.out.println(meinKleinesDreieck.getClass().getName());
		System.out.println(meinKleinesDreieck.getFlaeche());
		meinKleinesDreieck.setFarbet(Color.BLACK);
		
		
		System.out.println(((Dreieck)meinKleinesDreieck).getMeineFarbe());
	}
	
}
