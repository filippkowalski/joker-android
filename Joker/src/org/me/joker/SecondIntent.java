package org.me.joker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class SecondIntent extends Activity{
	
	private String cat = "kategoria";
	
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.second);
	        
	        /*Stworzenie view
	         * Wpisanie kategorii w naglowek
	         */
	        if (savedInstanceState == null){
	        	Bundle extras = getIntent().getExtras();
	        	if (extras == null){
	        		cat = null;
	        	}
	        	else {
	        		cat = extras.getString("CATEGORY");
	        	}
	        }
	        else {
	        	cat = (String) savedInstanceState.getSerializable("CATEGORY");
	        }
	        
	        TextView kat = (TextView)findViewById(R.id.category);
	        kat.setText(cat);
	        
	        TextView kawal = (TextView)findViewById(R.id.joke);
	        
	        Button powrot = (Button)findViewById(R.id.back);
	        powrot.setOnClickListener(new OnClickListener(){
	        	public void onClick(View view){
	        		SecondIntent.this.finish();
	        	}
	        });
	        
	        /*Otworzenie bazy danych
	         * Pobranie kawalu do TextView
	         */
	        
	      
	       
	       DatabaseAdapter db = new DatabaseAdapter(cat, getApplicationContext());
	        
	       String joke = db.loadJoke();
	        
	       kawal.setText(joke);
	 }
	 
}
