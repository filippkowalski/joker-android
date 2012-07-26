package org.me.joker;

import android.app.ListActivity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


 
public class JokerActivity extends ListActivity{
  
  
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        ListAdapter adapter = createAdapter();
        setListAdapter(adapter);
    }
 
    /**
     * Creates and returns a list adapter for the current list activity
     * @return
     */

    protected ListAdapter createAdapter()
    {
    	String[] categories = new String[] {
    			"Chuck Norris",
    			"Zboczone",
    			"O babie",
    			"O bacy",
    			"O blondynkach",
    			"O duchownych",
    			"O facetach",
    			"O Jasiu",
    			"O kobietach",
    			"O lekarzach",
    			"O pijakach",
    			"O policjantach",
    			"O studentach",
    			"O tesciowej",
    			"O zwierzetach",
    			"Turbo suchary"
    	};
 
    	// Create a simple array adapter (of type string) with the test values
    	ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories);
 
    	return adapter;
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	startActivity(new Intent(this, SecondIntent.class)); 
    }
   
}