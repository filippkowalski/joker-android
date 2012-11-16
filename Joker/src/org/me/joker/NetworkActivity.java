package org.me.joker;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/*
 * 
 * Klasa ta po wywo³aniu ma pobraæ najnowsz¹ baze danych i zapisaæ j¹ do bazy SQlite
 * 
 */

public class NetworkActivity {
	
	// All static variables
	static final String URL = "http://joker.dkowalski.com.hostingasp.pl/api/jokes/?format=xml";
	// XML node keys
	static final String KEY_ITEM = "Joke"; // parent node
	static final String KEY_NAME = "JokeId";
	static final String VOTESDOWN = "VotesDown";
	static final String VOTESUP = "VotesUp";
	
	public void getAndSaveXmlData(){
		
		//pobieranie danych
		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element
		 
		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		
		//zmienne
		String votesUp = "";
		String votesDown = "";
		String guidLocal = "";
		String guidServer = "";
		
		//zapisanie danych do tablicy e
		for (int i = 0; i < nl.getLength(); i++) {
	    	Element e = (Element) nl.item(1);
			
		    votesUp = parser.getValue(e, VOTESUP); 
		    votesDown = parser.getValue(e, VOTESDOWN); 
		    guidServer = parser.getValue(e, KEY_NAME);
	    }		
		
		//zapisanie danych do bazy
		
	}
}
