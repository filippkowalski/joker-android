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
	static final String URL = "http://joker.dkowalski.com.hostingasp.pl/api/jokes/";
	// XML node keys
	static final String KEY_ITEM = "Joke"; // parent node
	static final String KEY_NAME = "JokeId";
	static final String VOTESDOWN = "VotesDown";
	static final String VOTESUP = "VotesUp";
	
	public void updateSqliteVoteDb(){
		
		//pobieranie danych
		XMLParser parser = new XMLParser();
		String xml = parser.getXmlFromUrl(URL); // getting XML
		Document doc = parser.getDomElement(xml); // getting DOM element
		 
		NodeList nl = doc.getElementsByTagName(KEY_ITEM);
		
		//zmienne String
		String votesUp = "";
		String votesDown = "";
		String guidServer = "";
		
		//zmienne int
		int catId = 2;		
				
		//zapisanie danych do tablicy e
		for (int i = 0; i < nl.getLength(); i++) {
			//otworzenie bazy
			DatabaseAdapter dba = new DatabaseAdapter(catId, null, 0);
			
	    	Element e = (Element) nl.item(i);
			
		    votesUp = parser.getValue(e, VOTESUP); 
		    votesDown = parser.getValue(e, VOTESDOWN); 
		    guidServer = parser.getValue(e, KEY_NAME);
		    int k = dba.getLastInsertedID();
			    for(int nrKawalu = 1; nrKawalu <= k; nrKawalu++){
			    	if(dba.loadGuid(catId, nrKawalu).equals(guidServer)){
			    		//zapisanie do bazy danych 
			    		dba.saveToDb("voteup", votesUp, nrKawalu, catId);	
			    		dba.saveToDb("votedown", votesDown, nrKawalu, catId);
			    	}
			    }
	    }		
	}
}
