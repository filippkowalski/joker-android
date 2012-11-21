package org.me.joker;

import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DialogActivity extends DialogFragment {
	
	private Joke joke;
	private NetworkActivity networkManager;
	
	public DialogActivity(Joke joke, NetworkActivity nM){
		this.joke = joke;
		networkManager = nM;
	}
	
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.ocenianie)
               .setPositiveButton(R.string.tak, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
   		        		networkManager.voteUploadPlus(joke.getGuidFromDb());
                   }
               })
               .setNegativeButton(R.string.anuluj, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   
                   }
               })
               .setNeutralButton(R.string.nie, new DialogInterface.OnClickListener() {
            	   public void onClick(DialogInterface dialog, int id) {
  		        		networkManager.voteUploadMinus(joke.getGuidFromDb());
				}
			});
        
        return builder.create();
	}
}
