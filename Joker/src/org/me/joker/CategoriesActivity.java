package org.me.joker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class CategoriesActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) throws CursorIndexOutOfBoundsException{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.category);
        
        
        //Buttony prowadzace do kawalow
        
        
        //blondynki
        ImageButton blondes = (ImageButton)findViewById(R.id.oBlondynkach);
        blondes.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(3, "O Blondynkach");
        	}
        });
               
        //ulubione
        ImageButton ulubione = (ImageButton)findViewById(R.id.ulubione);
        ulubione.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(1, "Ulubione");
        	}
        });
        
        //chuck norris
        ImageButton chuckNorris = (ImageButton)findViewById(R.id.chuckNorris);
        chuckNorris.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(8, "Chuck Norris");
        	}
        });
        
        //o kobietach
        ImageButton oKobietach = (ImageButton)findViewById(R.id.oKobietach);
        oKobietach.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(5, "O Kobietach");
        	}
        });
        
        //zboczone
        ImageButton zboczone = (ImageButton)findViewById(R.id.zboczone);
        zboczone.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(9, "Zboczone");
        	}
        });
        
        //o jasiu
        ImageButton oJasiu = (ImageButton)findViewById(R.id.oJasiu);
        oJasiu.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(2, "O Jasiu");
        	}
        });
        
        //chamskie
        ImageButton chamskie = (ImageButton)findViewById(R.id.chamskie);
        chamskie.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(11, "Chamskie");
        	}
        });
        
        //o facetach
        ImageButton oFacetach = (ImageButton)findViewById(R.id.ofacetach);
        oFacetach.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(4, "O facetach");
        	}
        });
        
        //o zwierzetach
        ImageButton oZwierzetach = (ImageButton)findViewById(R.id.ozwierzetach);
        oZwierzetach.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(7, "O zwierzêtach");
        	}
        });
        
        //o studentach
        ImageButton oStudentach = (ImageButton)findViewById(R.id.ostudentach);
        oStudentach.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(6, "O studentach");
        	}
        });

        //turbo suchary
        ImageButton turboSuchary = (ImageButton)findViewById(R.id.turboSuchary);
        turboSuchary.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(10, "Turbo Suchary");
        	}
        });       

               
        //losowe
        ImageButton losowe = (ImageButton)findViewById(R.id.losowe);
        losowe.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(0, "Losowe");
        	}
        });
        
        //pobieranie aktualnej wersji bazy danych z serwera
        
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        
        boolean connectionAllow = sharedPref.getBoolean("internet", true);
    
    	final NetworkActivity networkManager = new NetworkActivity();
    	if(networkManager.haveNetworkConnection(getApplicationContext()) && connectionAllow){
    		Thread t = new Thread(){
    			public void run(){
    				networkManager.updateSqliteVoteDb();
    				
    				Looper.prepare();
    				
    				makeToast("Uaktualniono bazê ocen");
    				
    				Looper.loop();
    			}
    		};
    		t.start();
    	}
    	else{
    		makeToast("Brak po³¹czenia z internetem, oceny mog¹ byc nieaktualne");
    	}   
    
	}
	
	

    //Funkcja, która tworzy nowy intent wraz z przekazywaniem informacji o kategorii. 
    
    private void runSecondIntent(int catId, String catName){
		Intent intent = new Intent(getApplicationContext(), SecondIntent.class);
		intent.putExtra("ID", (int)catId);
		intent.putExtra("CATEGORY", catName);
		startActivity(intent);
    }
    
    
    public void makeToast(String msg){
    	Toast toast = Toast.makeText(getBaseContext(),msg,Toast.LENGTH_SHORT);
		
		LayoutInflater inflater = getLayoutInflater();
		View toastView = inflater.inflate(R.layout.toast, (ViewGroup)findViewById(R.id.toast));
		
		TextView toastText = (TextView)toastView.findViewById(R.id.textView1);
		
		toastText.setText(msg);
		
		toast.setView(toastView);
		toast.show();
    }
    
    
}
