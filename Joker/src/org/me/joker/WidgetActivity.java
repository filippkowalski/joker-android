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
	
    // pobierz wszystkie ID's widgetu
    ComponentName thisWidget = new ComponentName(context,WidgetActivity.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);

    // tworzenie intentu do obs³ugi tego activity
    Intent intent = new Intent(context.getApplicationContext(),
        UpdateWidgetService.class);
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

    // update widgetu
    context.startService(intent);
  }
} 