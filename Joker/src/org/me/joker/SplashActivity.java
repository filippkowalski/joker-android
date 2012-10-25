package org.me.joker;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity {
	private long splashDelay = 2000; //2 seconds 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.splash);
        
        //timer allows to show splash screen
        TimerTask task = new TimerTask(){
			@Override
			public void run() {
				finish();
				Intent mainIntent = new Intent().setClass(SplashActivity.this, JokerActivity.class);
				startActivity(mainIntent);
			}        	
        };
        
        Timer timer = new Timer();
        timer.schedule(task, splashDelay);
    }
}