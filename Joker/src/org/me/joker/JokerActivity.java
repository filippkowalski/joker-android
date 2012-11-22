package org.me.joker;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


 
public class JokerActivity extends Activity{
	
	private final static String APP_PNAME = "org.me.joker";  
	
	
	
	//przy kliknieciu 'menu'
	@Override
	 public boolean onCreateOptionsMenu(Menu menu) {
	     getMenuInflater().inflate(R.menu.menu, menu);
	     return true;
	}
	
	//obsluga przyciskow menu
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
		
        int itemId = item.getItemId();
        
		if (itemId == R.id.item1) {
			Intent i1 = new Intent(Intent.ACTION_SEND);
    		i1.setType("message/rfc822");
    		i1.putExtra(Intent.EXTRA_EMAIL  , new String[]{"void.studio7@gmail.com"});
    		i1.putExtra(Intent.EXTRA_SUBJECT, "Propozycja kawa³u dla aplikacji Joker");
    		i1.putExtra(Intent.EXTRA_TEXT   , "Zaproponuj nam kawa³ - jeœli bêdzie dobry, uwzglêdnimy go w kolejnym updeacie, a Tobie damy o tym znaæ ;)");
    		try {
    			startActivity(Intent.createChooser(i1, "Wysy³anie maila..."));
    		} catch (android.content.ActivityNotFoundException ex) {
    			Toast.makeText(JokerActivity.this, "Nie masz ¿adnych klientów pocztowych do wykonania tej akcji...", Toast.LENGTH_SHORT).show();
    		}
		} else if (itemId == R.id.item2) {
        	setContentView(R.layout.poradnik); 
		} else if (itemId == R.id.item3) {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:VoidStudio")));
		} else if (itemId == R.id.item4){
			Intent i1 = new Intent(Intent.ACTION_SEND);
    		i1.setType("message/rfc822");
    		i1.putExtra(Intent.EXTRA_EMAIL  , new String[]{"void.studio7@gmail.com"});
    		i1.putExtra(Intent.EXTRA_SUBJECT, "Sugestie odnoœnie aplikacji Joker");
    		i1.putExtra(Intent.EXTRA_TEXT   , "Bêdziemy wdziêczni za ka¿de sugestie dotycz¹ce aplikacji. Dziêkujemy;).");
    		try {
    			startActivity(Intent.createChooser(i1, "Wysy³anie maila..."));
    		} catch (android.content.ActivityNotFoundException ex) {
    			Toast.makeText(JokerActivity.this, "Nie masz ¿adnych klientów pocztowych do wykonania tej akcji...", Toast.LENGTH_SHORT).show();
    		}
		}
		
 
        return true;
    }
 
	//g³ówne okno
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