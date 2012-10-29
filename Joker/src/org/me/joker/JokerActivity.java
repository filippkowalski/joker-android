package org.me.joker;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;


 
public class JokerActivity extends Activity{
	
	private final static String APP_PNAME = "org.me.joker";  
  
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);
                
        //Je�li potrzebne tworzy baz� lub j� uaktualnia
        DatabaseHelper dbh = new DatabaseHelper(this);
        try{
        	dbh.createDatabase();
        }
        catch (IOException e){
        	throw new Error("Error creating database.");
        }
        dbh.close();
        
        
        //Buttony i ich funkcje
        
        //button przekierowujacy do kategorii
        ImageButton categories = (ImageButton)findViewById(R.id.kawaly);
        categories.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
        		startActivityForResult(intent, 1337);
        	}
        });        
       
        //button przekierowujacy do ustawien
        ImageButton settings = (ImageButton)findViewById(R.id.ustawienia);
        settings.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        		startActivityForResult(intent, 1337);
        	}
        });
        
        //button przekierowujacy do 'polub nas na fejsie'
        ImageButton polubnasnafb = (ImageButton)findViewById(R.id.polubnasnafb);
        polubnasnafb.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
		        String JokerUrl ="https://www.facebook.com/voidstd";
		        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(JokerUrl)));
	        	}
        });        
        
        //button przekierowujacy do 'ocen aplikacje'
        ImageButton ocen = (ImageButton)findViewById(R.id.ocen);
        ocen.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
	        	}
        });
        
        //button od "zaproponuj kawal"
        ImageButton zaproponuj = (ImageButton)findViewById(R.id.zaproponujkawal);
        zaproponuj.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		Intent i1 = new Intent(Intent.ACTION_SEND);
        		i1.setType("message/rfc822");
        		i1.putExtra(Intent.EXTRA_EMAIL  , new String[]{"void.studio7@gmail.com"});
        		i1.putExtra(Intent.EXTRA_SUBJECT, "Propozycja kawa�u dla aplikacji Joker");
        		i1.putExtra(Intent.EXTRA_TEXT   , "Zaproponuj nam kawa� - je�li b�dzie dobry, uwzgl�dnimy go w kolejnym updeacie, a Tobie damy o tym zna� ;)");
        		try {
        			startActivity(Intent.createChooser(i1, "Wysy�anie maila..."));
        		} catch (android.content.ActivityNotFoundException ex) {
        			Toast.makeText(JokerActivity.this, "Nie masz �adnych klient�w pocztowych do wykonania tej akcji...", Toast.LENGTH_SHORT).show();
        		}
        	}    
        });
        
        //button przekierowujacy do 'o nas'
        ImageButton onas = (ImageButton)findViewById(R.id.onas);
        onas.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		setContentView(R.layout.onas);
        	}    
        });
        	        
    
        
    }
   
}