package org.me.joker;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
		int catId = 1;
						
		//zapisanie danych do tablicy e
		for (int i = 0; i < nl.getLength(); i++) {
	    	Element e = (Element) nl.item(i);
			
		    votesUp = parser.getValue(e, VOTESUP); 
		    votesDown = parser.getValue(e, VOTESDOWN); 
		    guidServer = parser.getValue(e, KEY_NAME);
		    
		    int jokeId = 0;
		    
		    DatabaseAdapter dba = new DatabaseAdapter(catId + 1, null, 0);
		    
		    while (catId <= 11 && jokeId == 0){
		    	catId++;
		    	jokeId = dba.searchIfTheresGuid(catId, guidServer);
		    }
		    
		    dba.saveToDb("voteup", votesUp, jokeId, catId);
		    dba.saveToDb("votedown", votesDown, jokeId, catId);
		    /*
		    for(catId = 2; catId < 12; catId++){
		    	try{
				
				    
				    for(int nrKawalu = 1; nrKawalu <= iloscKawalow; nrKawalu++){
			    		dba.saveToDbByGuid("voteup", votesUp, nrKawalu, catId, guidServer);	
			    		dba.saveToDbByGuid("votedown", votesDown, nrKawalu, catId, guidServer);
				    }
		    	}
		    	catch (SQLException e1) {
		    	    
		    	}
		    }
		    */

			catId = 1;
	    }		
	}
	
	public void voteUploadPlus(String guid){
		String xml = " ";
		try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(URL+guid+"/?VoteUp=true");  
            
            HttpEntity entity = new StringEntity(xml);
            httpPut.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPut);
            
            //wysy³anie danych
            httpPut.setHeader("Content-Type", "application/xml");
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void voteUploadMinus(String guid){
		String xml = " ";
		try {
            // defaultHttpClient
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut(URL+guid+"/?VoteUp=false");  
            
            HttpEntity entity = new StringEntity(xml);
            httpPut.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPut);
            
            //wysy³anie danych
            httpPut.setHeader("Content-Type", "application/xml");
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public boolean haveNetworkConnection(Context context) {
	    boolean haveConnectedWifi = false;
	    boolean haveConnectedMobile = false;

	    ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
	    for (NetworkInfo ni : netInfo) {
	        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
	            if (ni.isConnected())
	                haveConnectedWifi = true;
	        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
	            if (ni.isConnected())
	                haveConnectedMobile = true;
	    }
	    return haveConnectedWifi || haveConnectedMobile;
	}
}
