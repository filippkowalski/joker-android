package org.me.joker;


import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service {
	private static final String LOG = "de.vogella.android.widget.example";

	  @Override
	  public void onStart(Intent intent, int startId) {
	    Log.i(LOG, "Called");
	    // Create some random data

	    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
	        .getApplicationContext());

	    int[] allWidgetIds = intent
	        .getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

	    ComponentName thisWidget = new ComponentName(getApplicationContext(),
	        WidgetActivity.class);
	    int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);
	    Log.w(LOG, "From Intent" + String.valueOf(allWidgetIds.length));
	    Log.w(LOG, "Direct" + String.valueOf(allWidgetIds2.length));

	    for (int widgetId : allWidgetIds) {	    	
	            
	      RemoteViews remoteViews = new RemoteViews(this.getApplicationContext().getPackageName(), R.layout.widget_layout);
	      
	      //pobranie kawalu
	      Joke joke;
	      
	      joke = new Joke(10, getApplicationContext());
	      
	      // ustawienie kawalu	      
	      joke.setRandomTurboSucharJoke();
	      String kawal = joke.getContent();
	      remoteViews.setTextViewText(R.id.update, "Suchar dnia:"+'\n'+kawal);

	      // Register an onClickListener
	      Intent clickIntent = new Intent(this.getApplicationContext(),
	    		  WidgetActivity.class);

	      clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
	      clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
	          allWidgetIds);

	      PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent,
	          PendingIntent.FLAG_UPDATE_CURRENT);
	      remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
	      appWidgetManager.updateAppWidget(widgetId, remoteViews);
	    }
	    stopSelf();

	    super.onStart(intent, startId);
	  }

	  @Override
	  public IBinder onBind(Intent intent) {
	    return null;
	  }
} 