package org.me.joker;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;


 
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
}