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

	@Override
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
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
	        
	        final Joke joke;
	        
	        if (catId == 0){
	        	joke = new Joke(getApplicationContext());
	        }
	        else{
	        	joke = new Joke(catId, getApplicationContext());
	        }
	        
	      
	        
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
	        	if (!catName.contains("Losowe")){
	        		nr.setText(joke.getNumber());
	        	}
	        	else
	        		nr.setText("");
	        }
	        catch(Exception e){
	        	Toast toast = Toast.makeText(getBaseContext(),"Brak ulubionych kawałów",Toast.LENGTH_SHORT);
		        toast.show();
		        SecondIntent.this.finish();
	        }
	        
	      
	        
	        ImageButton socialshare = (ImageButton)findViewById(R.id.socialshare);
	        socialshare.setOnClickListener(new OnClickListener(){
	        	public void onClick(View view){
	        		
	        		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
	        		sharingIntent.setType("text/plain");
	        		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, joke.getContent() +'\n'+"Kawał znaleziony w aplikacji Joker - http://bitly.com/TxgVn6");
	        		sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Dobry kawał ;)");
	        		startActivity(Intent.createChooser(sharingIntent, "Share using:"));
	        		// czemu to nie dziala ? ? -> database.close();
	        	}
	        });
	        
			ImageButton poprzedni = (ImageButton)findViewById(R.id.previous);
			poprzedni.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					if (!catName.contains("Losowe")){
							try{				
							kawal.setText(joke.getPrevious());
							joke.onPreviousButtonClick();
							//licznik
					        final TextView nr = (TextView)findViewById(R.id.nr);
					        nr.setText(joke.getNumber());
					        
					        kawal.scrollTo(0, 0);
						}
						catch(Exception e){
							
						}
					}
					
					else{
						joke.setRandomJoke();
						kawal.setText(joke.getContent());
						
						kawal.scrollTo(0, 0);
					}
						
				}
			});
			ImageButton nastepny = (ImageButton)findViewById(R.id.next);
			nastepny.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					if (!catName.contains("Losowe")){
							try{
							kawal.setText(joke.getNext());
							joke.onNextButtonClick();
							//licznik
					        final TextView nr = (TextView)findViewById(R.id.nr);
					        nr.setText(joke.getNumber());
					        
					        kawal.scrollTo(0, 0);
						}
						catch(Exception e){
							
						}
					}
					else{
						joke.setRandomJoke();
						kawal.setText(joke.getContent());
						
						kawal.scrollTo(0, 0);
					}
					
				}
			});
			
			ImageButton ulub =(ImageButton)findViewById(R.id.fav);
			ulub.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					
					// Jesli jestesmy w kategorii ulubione to przycisk ten usunie kawal z tejze kategorii
					 
					if(catName.contains("ULUBIONE")){
						joke.deleteFromFavourites();
						try{
							kawal.setText(joke.getPrevious());
							joke.onPreviousButtonClick();
							kawal.scrollTo(0, 0);
				        	 
				        	Toast toast = Toast.makeText(getBaseContext(),"Kawał został usunięty z ulubionych ;(",Toast.LENGTH_SHORT);
					        toast.show();
				         }
				         catch(Exception e){
				        	 kawal.setText("Brak kawału do wyświetlenia w wybranej kategorii");
				        }
					}
					else{
						joke.addToFavourites();
			             
			            Toast toast = Toast.makeText(getBaseContext(),"Kawał został dodany do ulubionych!",Toast.LENGTH_SHORT);
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
	        	kawal.setText(joke.getContent());
	         }
	         catch(Exception e){
	        	kawal.setText("Brak kawału do wyświetlenia w wybranej kategorii.");
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

		final Joke joke = new Joke(catId, getApplicationContext());
        final TextView kawal = (TextView)findViewById(R.id.joke);
		
		if (predictions.size() > 0) {
			Prediction prediction = predictions.get(0);
			if (prediction.score > 1.0) {
				
				if(prediction.name.contains("previous")){
					if (!catName.contains("Losowe")){
						try{				
							kawal.setText(joke.getNext());
							joke.onNextButtonClick();
							//licznik
					        final TextView nr = (TextView)findViewById(R.id.nr);
					        nr.setText(joke.getNumber());
					        
					        kawal.scrollTo(0, 0);
						}
						catch(Exception e){
							
						}
					}
					else{
						joke.setRandomJoke();
						kawal.setText(joke.getContent());
						
						kawal.scrollTo(0, 0);
					}
					
					
				}  
				if(prediction.name.contains("next")){
					if(!catName.contains("Losowe")){
						try{				
							kawal.setText(joke.getPrevious());
							joke.onPreviousButtonClick();
							//licznik
					        final TextView nr = (TextView)findViewById(R.id.nr);
					        nr.setText(joke.getNumber());
					        
					        kawal.scrollTo(0, 0);
						}
						catch(Exception e){
							
						}
					}
					else{
						joke.setRandomJoke();
						kawal.setText(joke.getContent());
						
						kawal.scrollTo(0, 0);
					}
					
				}
			}
		}
	}
}
