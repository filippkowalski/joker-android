package org.me.joker;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SecondIntent extends Activity implements OnGesturePerformedListener{
	
	private GestureLibrary mLibrary;
	
	private int catId = 1;
	private String catName = "Kategoria";
	
	
	//przy kliknieciu 'menu'
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	     getMenuInflater().inflate(R.menu.menu, menu);
	     return true;
	}
	
	//obsluga przyciskow menu
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
 
        case R.id.item1: // wcisniecie przycisku ulubione

        	
        	final DatabaseAdapter db = new DatabaseAdapter(catId, getApplicationContext());
            final TextView kawal = (TextView)findViewById(R.id.joke);
        	
        	/*
			 * Jesli jestesmy w kategorii ulubione to przycisk ten usunie kawal z tejze kategorii
			 */
			if(catName.contains("ULUBIONE")){
				db.deleteJokeFromFavourites(catId);
				try{
		        	 db.setLastJokeMinus(catId);
		        	 String joke = db.loadJoke(catId);
		        	 kawal.setText(joke);
		        	 
		        	 Toast toast = Toast.makeText(getBaseContext(),"Kawał został usunięty ;(",Toast.LENGTH_SHORT);
			         toast.show();
		         }
		         catch(Exception e){
		        	 try{
		        		 db.setLastJokePlus(catId);
		        		 db.setLastJokePlus(catId);
		        		 String joke = db.loadJoke(catId);
		        		 kawal.setText(joke);
		        		 
		        		 Toast toast = Toast.makeText(getBaseContext(),"Kawał został usunięty ;(",Toast.LENGTH_SHORT);
				         toast.show();
		        	 }
		        	 catch(Exception ex){
		        		 kawal.setText("Brak kawału do wyświetlenia w wybranej kategorii");
		        	 }
		         }
			}
			else{
				db.addJokeToFavourites(kawal.getText().toString());
	             
	            Toast toast = Toast.makeText(getBaseContext(),"Zajebiście! Kawał został dodany!",Toast.LENGTH_SHORT);
	            toast.show();
			}
        	
            break;
        case R.id.item2: // przycisk drugi - wyslij kawal znajomemu
        	
        	DatabaseAdapter database = new DatabaseAdapter(catId, getApplicationContext());
         	
        	        	Intent i = new Intent(Intent.ACTION_SEND);
        	        	i.setType("message/rfc822");
        	        	i.putExtra(Intent.EXTRA_EMAIL  , new String[]{""});
        	        	i.putExtra(Intent.EXTRA_SUBJECT, "Fajny kawał - polecam ;)");
        	        	i.putExtra(Intent.EXTRA_TEXT   , database.loadJoke(catId)+" " +
        	        			"Kawał wysłany z aplikacji Joker - kawały dowcipy, dostępny na Google Play - http://bit.ly/TxgVn6 ");
        	        	try {
        	        	    startActivity(Intent.createChooser(i, "Wysyłanie maila..."));
        	        	} catch (android.content.ActivityNotFoundException ex) {
        	        	    Toast.makeText(SecondIntent.this, "Nie masz żadnych klientów pocztowych do wykonania tej akcji...", Toast.LENGTH_SHORT).show();
        	        	}
        	
            break;
            
        case R.id.item3:  //zaproponuj nam kawał
			
    		Intent i1 = new Intent(Intent.ACTION_SEND);
        	i1.setType("message/rfc822");
        	i1.putExtra(Intent.EXTRA_EMAIL  , new String[]{"void.studio7@gmail.com"});
        	i1.putExtra(Intent.EXTRA_SUBJECT, "Propozycja kawału");
        	i1.putExtra(Intent.EXTRA_TEXT   , "Zaproponuj nam kawał - jeśli będzie dobry, uwzględnimy go w kolejnym updeacie, a Tobie damy o tym znać ;)");
        	try {
        	    startActivity(Intent.createChooser(i1, "Wysyłanie maila..."));
        	} catch (android.content.ActivityNotFoundException ex) {
        	    Toast.makeText(SecondIntent.this, "Nie masz żadnych klientów pocztowych do wykonania tej akcji...", Toast.LENGTH_SHORT).show();
        	}
        	
    		
    			break;
            
            
        case R.id.item4:
 	        SecondIntent.this.finish();        // przycisk powrot
            break; 
            
        }     
 
        return true;
    }
 
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        
	        //to rozumiem ma chowac pasek u gory jo ?
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
	                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        
	        setContentView(R.layout.second); 
	     
	       
	        /*
	         * Przejecie danych z pierwszego okna
	         */
	        Bundle bundle = getIntent().getExtras();
	        catId = bundle.getInt("ID");
	        catName = bundle.getString("CATEGORY");
	        
	        final DatabaseAdapter db = new DatabaseAdapter(catId, getApplicationContext());
	      
	        
	        /*
	         * Stworzenie view oraz wpisanie kategorii w naglowek
	         */
	        final TextView kat = (TextView)findViewById(R.id.category);
	        kat.setText(catName);
	        
	        final TextView kawal = (TextView)findViewById(R.id.joke);
	        /*
	         * Sprawienie, ze pole tekstowe mozna przewijac
	         */
	        kawal.setMovementMethod(new ScrollingMovementMethod());
	        
     
	        //licznik
	        try{
	        	final TextView nr = (TextView)findViewById(R.id.nr);
		        String number = Integer.toString(db.getLastJoke(catId));
		        String lastNumber = Integer.toString(db.getLastInsertedID());
		        nr.setText(number+"/"+lastNumber);
	        }
	        catch(Exception e){
	        	Toast toast = Toast.makeText(getBaseContext(),"Brak ulubionych kawałów",Toast.LENGTH_SHORT);
		        toast.show();
		        SecondIntent.this.finish();
	        }
	        
	        /*
	        //przyciski
	        Button powrot = (Button)findViewById(R.id.back);
	        powrot.setOnClickListener(new OnClickListener(){
	        	public void onClick(View view){
	        		SecondIntent.this.finish();
	        	}
	        });
	        */
	        
			ImageButton poprzedni = (ImageButton)findViewById(R.id.previous);
			poprzedni.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					try{
						db.setLastJokeMinus(catId);					
						kawal.setText(db.loadJoke(catId));
						//licznik
				        final TextView nr = (TextView)findViewById(R.id.nr);
				        String number = Integer.toString(db.getLastJoke(catId));
				        String lastNumber = Integer.toString(db.getLastInsertedID());
				        nr.setText(number+"/"+lastNumber);
				        
				        kawal.scrollTo(0, 0);
					}
					catch(Exception e){
						
					}
					
				}
			});
			ImageButton nastepny = (ImageButton)findViewById(R.id.next);
			nastepny.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					try{
						
						db.setLastJokePlus(catId);
						kawal.scrollTo(0, 0);
						kawal.setText(db.loadJoke(catId));
						//licznik
				        final TextView nr = (TextView)findViewById(R.id.nr);
				        String number = Integer.toString(db.getLastJoke(catId));
				        String lastNumber = Integer.toString(db.getLastInsertedID());
				        nr.setText(number+"/"+lastNumber);
				        
				        kawal.scrollTo(0, 0);
					}
					catch(Exception e){
						
					}
					
				}
			});
			
			ImageButton ulub =(ImageButton)findViewById(R.id.favourite);
			ulub.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					
					// Jesli jestesmy w kategorii ulubione to przycisk ten usunie kawal z tejze kategorii
					 
					if(catName.contains("ULUBIONE")){
						db.deleteJokeFromFavourites(catId);
						try{
				        	 db.setLastJokeMinus(catId);
				        	 String joke = db.loadJoke(catId);
				        	 kawal.scrollTo(0, 0);
				        	 kawal.setText(joke);
				        	 
				        	 Toast toast = Toast.makeText(getBaseContext(),"Kawał został usunięty ;(",Toast.LENGTH_SHORT);
					         toast.show();
				         }
				         catch(Exception e){
				        	 try{
				        		 db.setLastJokePlus(catId);
				        		 db.setLastJokePlus(catId);
				        		 String joke = db.loadJoke(catId);
				        		 kawal.scrollTo(0, 0);
				        		 kawal.setText(joke);
				        		 
				        		 Toast toast = Toast.makeText(getBaseContext(),"Kawał został usunięty ;(",Toast.LENGTH_SHORT);
						         toast.show();
				        	 }
				        	 catch(Exception ex){
				        		 kawal.setText("Brak kawalu do wyswietlenia w wybranej kategorii");
				        	 }
				         }
					}
					else{
						db.addJokeToFavourites(kawal.getText().toString());
			             
			            Toast toast = Toast.makeText(getBaseContext(),"Kawał został dodany!",Toast.LENGTH_SHORT);
			            toast.show();
					}
					
				}
			});
			
			
			if(catName.contains("ULUBIONE")){
	        	ulub.setImageResource(R.drawable.removebutton);
	        }
			
	        
	        /*Otworzenie bazy danych
	         * Pobranie kawalu do TextView
	         */
	         try{
	        	 String joke = db.loadJoke(catId);
	        	 kawal.setText(joke);
	         }
	         catch(Exception e){
       		 
	        		 kawal.setText("Brak kawalu do wyswietlenia w wybranej kategorii");	        		 

	         }
	         
	        	 
			 
		     
		   //wczytanie biblioteki gestów
		        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		        if (!mLibrary.load()) {
		        	finish();
		        }
		        
		        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
		        gestures.setGestureVisible(false);
		        gestures.addOnGesturePerformedListener(this);
		        
	 }


	//metoda obslugujaca gesty
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = mLibrary.recognize(gesture);
		
		final DatabaseAdapter db = new DatabaseAdapter(catId, getApplicationContext());
        final TextView kawal = (TextView)findViewById(R.id.joke);
		
		if (predictions.size() > 0) {
			Prediction prediction = predictions.get(0);
			if (prediction.score > 1.0) {
				
				if(prediction.name.contains("previous")){
					try{
						db.setLastJokePlus(catId);					
						kawal.setText(db.loadJoke(catId));
						//licznik
				        final TextView nr = (TextView)findViewById(R.id.nr);
				        String number = Integer.toString(db.getLastJoke(catId));
				        String lastNumber = Integer.toString(db.getLastInsertedID());
				        nr.setText(number+"/"+lastNumber);
				        
				        kawal.scrollTo(0, 0);
					}
					catch(Exception e){
						
					}
					
				}  
				if(prediction.name.contains("next")){
					try{
						db.setLastJokeMinus(catId);					
						kawal.setText(db.loadJoke(catId));
						//licznik
				        final TextView nr = (TextView)findViewById(R.id.nr);
				        String number = Integer.toString(db.getLastJoke(catId));
				        String lastNumber = Integer.toString(db.getLastInsertedID());
				        nr.setText(number+"/"+lastNumber);
				        
				        kawal.scrollTo(0, 0);
					}
					catch(Exception e){
						
					}
				}
			}
		}
	}
}
