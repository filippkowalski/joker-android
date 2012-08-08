package org.me.joker;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondIntent extends Activity implements OnGesturePerformedListener{
	
	private GestureLibrary mLibrary;
	
	private int catId = 1;
	private String catName = "Kategoria";
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
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
	        TextView kat = (TextView)findViewById(R.id.category);
	        kat.setText(catName);
	        
	        final TextView kawal = (TextView)findViewById(R.id.joke);
	        
	        Button powrot = (Button)findViewById(R.id.back);
	        powrot.setOnClickListener(new OnClickListener(){
	        	public void onClick(View view){
	        		SecondIntent.this.finish();
	        	}
	        });
	        
	        
			Button poprzedni = (Button)findViewById(R.id.previous);
			poprzedni.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					try{
						db.setLastJokeMinus(catId);					
						kawal.setText(db.loadJoke(catId));
					}
					catch(Exception e){
						
					}
					
				}
			});
			
			Button nastepny = (Button)findViewById(R.id.next);
			nastepny.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					try{
						db.setLastJokePlus(catId);
						kawal.setText(db.loadJoke(catId));
					}
					catch(Exception e){
						
					}
					
				}
			});
	        
	        /*Otworzenie bazy danych
	         * Pobranie kawalu do TextView
	         */
	         try{
	        	 String joke = db.loadJoke(catId);  
	        	 kawal.setText(joke);
	         }
	         catch (Exception e){
	        	 kawal.setText("Brak kawalu do wyswietlenia w wybranej kategorii");
	         }
			 
		     
		   //wczytanie biblioteki gestów
		        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		        if (!mLibrary.load()) {
		        	finish();
		        }
		        
		        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
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
				
				if(prediction.name.contains("next")){
					try{
						db.setLastJokePlus(catId);					
						kawal.setText(db.loadJoke(catId));
					}
					catch(Exception e){
						
					}
					
				}  
				if(prediction.name.contains("previous")){
					try{
						db.setLastJokeMinus(catId);					
						kawal.setText(db.loadJoke(catId));
					}
					catch(Exception e){
						
					}
				}
			}
		}
	}
}
