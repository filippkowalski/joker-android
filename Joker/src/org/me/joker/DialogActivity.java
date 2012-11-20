package org.me.joker;

import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class DialogActivity extends DialogFragment {
	
	private Joke joke;
	private Context context;
	
	public DialogActivity(Joke joke, Context context){
		this.joke = joke;
		this.context = context;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.ocenianie)
               .setPositiveButton(R.string.tak, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   NetworkActivity networkManager = new NetworkActivity();
   		        		networkManager.voteUploadPlus(joke.getGuidFromDb());
   		        	
   		        		Toast toast = Toast.makeText(context,"Przes³ano ocenê, dziêkujemy.",Toast.LENGTH_SHORT);
   		        		toast.show();
                   }
               })
               .setNegativeButton(R.string.anuluj, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   
                   }
               })
               .setNeutralButton(R.string.nie, new DialogInterface.OnClickListener() {
            	   public void onClick(DialogInterface dialog, int id) {
            		   NetworkActivity networkManager = new NetworkActivity();
  		        		networkManager.voteUploadMinus(joke.getGuidFromDb());
  		        	
  		        		Toast toast = Toast.makeText(context,"Przes³ano ocenê, dziêkujemy.",Toast.LENGTH_SHORT);
  		        		toast.show();
				}
			});
        
        return builder.create();
	}
}
