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

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

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
		
		//flaga
		boolean trafiony = false;
				
		//zapisanie danych do tablicy e
		for (int i = 0; i < nl.getLength(); i++) {
	    	Element e = (Element) nl.item(i);
			
		    votesUp = parser.getValue(e, VOTESUP); 
		    votesDown = parser.getValue(e, VOTESDOWN); 
		    guidServer = parser.getValue(e, KEY_NAME);

		    catId = 2;
		    
		    do{
		    	//otworzenie bazy
				DatabaseAdapter dba = new DatabaseAdapter(catId, null, 0);
			    int iloscKawalow = dba.getLastInsertedID();
			    trafiony = false;
			    
			    //pêtla przeszukuj¹ca bazê w poszukiwaniu odpowiedniego miejsca
			    for(int nrKawalu = 1; nrKawalu <= iloscKawalow; nrKawalu++){
			    	if(dba.loadGuid(catId, nrKawalu).equals(guidServer)){
			    		//zapisanie do bazy danych 
			    		dba.saveToDb("voteup", votesUp, nrKawalu, catId);	
			    		dba.saveToDb("votedown", votesDown, nrKawalu, catId);
			    		trafiony = true;
			    	} 
			    }
			    catId++;			    
		    } while(trafiony == false);
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
