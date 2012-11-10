package org.me.joker;

import android.app.Activity;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class CategoriesActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) throws CursorIndexOutOfBoundsException{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.category);
        
        
        //Buttony prowadzace do kawalow
        
        
    /*  
        //blondynki
        ImageButton blondes = (ImageButton)findViewById(R.id.oBlondynkach);
        blondes.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(3, "O Blondynkach");
        	}
        });
      */  
        
        //chuck norris
        ImageButton chuckNorris = (ImageButton)findViewById(R.id.chuckNorris);
        chuckNorris.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(8, "Chuck Norris");
        	}
        });
        
        //ulubione
        ImageButton ulubione = (ImageButton)findViewById(R.id.ulubione);
        ulubione.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(1, "Ulubione");
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
    
	}
	
	

    //Funkcja, która tworzy nowy intent wraz z przekazywaniem informacji o kategorii. 
    
    private void runSecondIntent(int catId, String catName){
		Intent intent = new Intent(getApplicationContext(), SecondIntent.class);
		intent.putExtra("ID", (int)catId);
		intent.putExtra("CATEGORY", catName);
		startActivity(intent);
    }
}
