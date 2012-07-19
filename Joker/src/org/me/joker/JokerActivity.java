package org.me.joker;

import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;

 
public class JokerActivity extends Activity{
  
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        /*Jesli baza danych nie istnieje
         * to jest tworzona w tym miejscu
         */
        DatabaseHelper myDbHelper = new DatabaseHelper(this);
        
        try{
        	
        	myDbHelper.createDatabase();
        	
        }
        catch (IOException e){
        	throw new Error("Unable to create database");
        }
        
        myDbHelper.close();
        
        /*Stworzenie view
         * 
         */
        Button jasiu = (Button)findViewById(R.id.oJasiu);
        Button blondynki = (Button)findViewById(R.id.oBlondynkach);
        
        jasiu.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		newIntent("O Jasiu");
        	}
        });
        
        blondynki.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		newIntent("O Blondynkach");
        	}
        });
    }
    
    
    public void newIntent(String category){
    	Intent intent = new Intent(getApplicationContext(), SecondIntent.class);
    	intent.putExtra("CATEGORY", category);
    	startActivity(intent);
    };
}