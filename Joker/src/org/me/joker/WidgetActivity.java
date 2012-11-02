package org.me.joker;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class WidgetActivity extends AppWidgetProvider {

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) {

	//pobierz losowy kawa³
	final Joke joke;
	joke = new Joke(1, context);
	joke.setRandomJoke();
	
    // Get all ids
    ComponentName thisWidget = new ComponentName(context,
    		WidgetActivity.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

    // Build the intent to call the service
    Intent intent = new Intent(context.getApplicationContext(),
        UpdateWidgetService.class);
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

    // Update the widgets via the service
    context.startService(intent);
  }
} 