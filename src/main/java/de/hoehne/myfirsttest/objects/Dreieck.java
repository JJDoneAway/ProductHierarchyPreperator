package de.hoehne.myfirsttest.objects;

import java.awt.Color;

public class Dreieck implements Form{

	private double grundlinie;
	private double hoehe;
	private Color meineFarbe;
	
	
	public Dreieck(double grundlinie, double hoehe) {
		if(grundlinie <= 0 || hoehe <= 0){
			throw new IllegalArgumentException("Grundlinie und höhe müssen größer null sein.");
		}
		
		this.grundlinie = grundlinie;
		this.hoehe = hoehe;
		this.meineFarbe = Color.BLUE;
		
		
	}
	
	public Double getFlaeche() {
		
		return grundlinie * hoehe * 0.5;
	}
	
	
	public void setFarbet(Color myNewColor) throws IllegalArgumentException {
		if(myNewColor == null){
			throw new IllegalArgumentException("Die Farbe darf nicht null sein");
		}
		
	}
	
	public Color getMeineFarbe() {
		return meineFarbe;
	}
	
}
