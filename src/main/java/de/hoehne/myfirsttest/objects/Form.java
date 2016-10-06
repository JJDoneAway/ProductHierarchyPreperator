package de.hoehne.myfirsttest.objects;

import java.awt.Color;

public interface Form {

	public Double getFlaeche();
	
	/**
	 * hiermit kann man die Farbe ändern
	 * 
	 * @param myNewColor eine Farbe, die das Objekt annehmen soll
	 * 
	 * @throws IllegalArgumentException wenn die Farbe null ist
	 */
	public void setFarbet(Color myNewColor) throws IllegalArgumentException;
	
}
