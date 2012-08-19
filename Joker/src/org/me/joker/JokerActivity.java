package org.me.joker;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;


 
public class JokerActivity extends ListActivity{
  
  
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);
        
        getListView().setDividerHeight(0);
        
        ListAdapter adapter = createAdapter();
        setListAdapter(adapter);
        
        
        
        
        DatabaseHelper dbh = new DatabaseHelper(this);
        try{
        	dbh.createDatabase();
        }
        catch (IOException e){
        	throw new Error("Error creating database.");
        }
        dbh.close();
    }
 
    /**
     * Creates and returns a list adapter for the current list activity
     * @return
     */
    
    
    String[] category = new String[] {
    		"ULUBIONE",
			"O Jasiu",
			"O blondynkach",
			"O facetach",
			"O kobietach",
			"O studentach",
			"O zwierzetach",
			"Chuck Norris",
			"Zboczone",
			"Turbo suchary",
			"Chamskie"
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