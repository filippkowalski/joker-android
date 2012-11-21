package org.me.joker;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.TextView;


 
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
                
        //Jeœli potrzebne tworzy bazê lub j¹ uaktualnia
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

        
        //pobieranie aktualnej wersji bazy danych z serwera
        
	        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
	        
	        boolean connectionAllow = sharedPref.getBoolean("internet", true);
        
        	NetworkActivity networkManager = new NetworkActivity();
        	if(networkManager.haveNetworkConnection(getApplicationContext()) && connectionAllow){
            	networkManager.updateSqliteVoteDb();
        	}
        	else{
        		Toast toast = Toast.makeText(getBaseContext(),"Brak po³¹czenia z internetem, oceny mog¹ byc nieaktualne",Toast.LENGTH_SHORT);
        		
        		LayoutInflater inflater = getLayoutInflater();
        		View toastView = inflater.inflate(R.layout.toast, (ViewGroup)findViewById(R.id.toast));
        		
        		TextView toastText = (TextView)toastView.findViewById(R.id.textView1);
        		
        		toastText.setText("Brak po³¹czenia z internetem, oceny mog¹ byc nieaktualne");
        		
        		toast.setView(toastView);
        		toast.show();
        	}
    
        
    }
   
}