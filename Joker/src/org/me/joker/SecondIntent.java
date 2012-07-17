package org.me.joker;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

public class SecondIntent extends Activity{
	
	private String cat = "kategoria";
	
	public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.second);
	        
	        if (savedInstanceState == null){
	        	Bundle extras = getIntent().getExtras();
	        	if (extras == null){
	        		cat = "dupa";
	        	}
	        	else {
	        		cat = extras.getString("CATEGORY");
	        	}
	        }
	        else {
	        	cat = (String) savedInstanceState.getSerializable("CATEGORY");
	        }
	        
	        TextView kat = (TextView)findViewById(R.id.category);
	        kat.setText(cat);
	        
	        Button powrot = (Button)findViewById(R.id.back);
	        powrot.setOnClickListener(new OnClickListener(){
	        	public void onClick(View view){
	        		SecondIntent.this.finish();
	        	}
	        });
	 }
	 
}
