package org.me.joker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class SecondIntent extends Activity{
	
	private int catId = 1;
	private String catName = "Kategoria";
	
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
	        
	        TextView kawal = (TextView)findViewById(R.id.joke);
	        
	        Button powrot = (Button)findViewById(R.id.back);
	        powrot.setOnClickListener(new OnClickListener(){
	        	public void onClick(View view){
	        		SecondIntent.this.finish();
	        	}
	        });
	        
	        
			Button poprzedni = (Button)findViewById(R.id.previous);
			poprzedni.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					Intent intent = new Intent(getApplicationContext(), SecondIntent.class);
					intent.putExtra("ID", (int)catId);
					intent.putExtra("CATEGORY", catName);
			    	startActivity(intent);
				}
			});
			
			Button nastepny = (Button)findViewById(R.id.next);
			nastepny.setOnClickListener(new OnClickListener(){
				public void onClick(View view){
					Intent intent = new Intent(getApplicationContext(), SecondIntent.class);
					intent.putExtra("ID", (int)catId);
					intent.putExtra("CATEGORY", catName);
					db.setLastJokePlus(catId);
			    	startActivity(intent);
				}
			});
	        
	        /*Otworzenie bazy danych
	         * Pobranie kawalu do TextView
	         */
	        
	      
			 String joke = db.loadJoke(catId);
		        
		       kawal.setText(joke);
	       
	 }
	 
}
