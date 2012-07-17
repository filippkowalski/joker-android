package org.me.joker;

import java.util.ArrayList;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
 
public class JokerActivity extends Activity{
  
 private final String DB_NAME = "bazaKategorii";
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        ArrayList<String> results = new ArrayList<String>();
         
        SQLiteDatabase bazaKategorii = null;
        
        try{         
         
        bazaKategorii = this.openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null); //stworzenie bazy lub otworzenie
          
        bazaKategorii.execSQL("CREATE TABLE IF NOT EXISTS Kategorie (numer VARCHAR, nazwaKategorii VARCHAR)");  
        bazaKategorii.execSQL("INSERT INTO Kategorie  Values('1','Zboczone');");
        bazaKategorii.execSQL("INSERT INTO Kategorie  Values('1','O Jasiu');");
        bazaKategorii.execSQL("INSERT INTO Kategorie  Values('1','O Blondynkach');");
        bazaKategorii.execSQL("INSERT INTO Kategorie  Values('1','Turbo suchary');");
       
       
      Cursor cursor = bazaKategorii.rawQuery("SELECT nazwaKategorii FROM Kategorie WHERE numer like '1'",null);
       
      if(cursor.moveToFirst()){ //Metoda zwraca FALSE jesli cursor jest pusty
       do{
        String numer = cursor.getString(cursor.getColumnIndex("nazwaKategorii"));
        results.add(numer);
       }while(cursor.moveToNext()); //Metoda zwraca FALSE wówczas gdy cursor przejdzie ostatni wpis
      }
          
        }
        catch(SQLiteException e) { 
         Log.e(getClass().getSimpleName(), "Could not create or Open the database"); }
        finally {
         if (bazaKategorii != null) 
         bazaKategorii.execSQL("DELETE FROM Kategorie");
         bazaKategorii.close();
        }
        
        
        
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