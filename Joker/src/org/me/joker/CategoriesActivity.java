package org.me.joker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CategoriesActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.category);
        
        
        //Buttony, które przechodz¹ do kategorii kawa³ów.
        
        Button blondes = (Button)findViewById(R.id.blondynki);
        blondes.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		runSecondIntent(3, "O Blondynkach");
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
