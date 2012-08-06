package org.me.joker;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.IOException;


 
public class JokerActivity extends ListActivity{
  
  
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        ListAdapter adapter = createAdapter();
        setListAdapter(adapter);
        
        DatabaseHelper dbh = new DatabaseHelper(this);
        try{
        	dbh.createDatabase();
        }
        catch (IOException e){
        	throw new Error("Error creating database.");
        }
    }
 
    /**
     * Creates and returns a list adapter for the current list activity
     * @return
     */
    
    
    String[] category = new String[] {
			"O Jasiu",
			"O blondynkach",
			"O babie",
			"O bacy",
			"O duchownych",
			"O facetach",
			"O kobietach",
			"O lekarzach",
			"O pijakach",
			"O policjantach",
			"O studentach",
			"O tesciowej",
			"O zwierzetach",
			"Chuck Norris",
			"Zboczone",
			"Turbo suchary"
	};

    protected ListAdapter createAdapter()
    {    
    	// Create a simple array adapter (of type string) with the test values
    	ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, category);
 
    	return adapter;
    }
        
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	Intent intent = new Intent(getApplicationContext(), SecondIntent.class);
		intent.putExtra("ID", (int)id);
		intent.putExtra("CATEGORY", category[(int)id]);
    	startActivity(intent);
    }
   
}