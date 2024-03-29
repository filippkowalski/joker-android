package org.me.joker;

import android.app.Activity;
import android.app.Dialog;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.DialogFragment;

public class DialogActivity extends DialogFragment {
	
	private Joke joke;
	private NetworkActivity networkManager;
	
	public DialogActivity(Joke joke, NetworkActivity nM){
		this.joke = joke;
		networkManager = nM;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.ocenianie)
               .setPositiveButton(R.string.tak, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   
                	   Thread t = new Thread(){
               			public void run(){               				
	               			Looper.prepare();
	               				
	               			networkManager.voteUploadPlus(joke.getGuidFromDb());
	               				
	               			Looper.loop();
	               			}
	               		};
	               		t.start(); 
   		        		
   		        		mListener.onDialogPositiveClick(DialogActivity.this);
                   }
               })
               .setNegativeButton(R.string.nie, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                	   
                	   Thread t = new Thread(){
                  			public void run(){               				
   	               			Looper.prepare();
   	               				
   	               			networkManager.voteUploadMinus(joke.getGuidFromDb());
   	               				
   	               			Looper.loop();
   	               			}
   	               		};
   	               		t.start();    	               		
                	   
 		        	mListener.onDialogNegativeClick(DialogActivity.this);
                   }
               })
               .setNeutralButton(R.string.anuluj, new DialogInterface.OnClickListener() {
            	   public void onClick(DialogInterface dialog, int id) {
            		   
				}
			});
        
        return builder.create();
	}
	
	
	/* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    
    // Use this instance of the interface to deliver action events
    NoticeDialogListener mListener;
    
    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }
    }
}
