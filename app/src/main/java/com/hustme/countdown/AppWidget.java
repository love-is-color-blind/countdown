package com.hustme.countdown;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AppWidget extends AppWidgetProvider {

    public static final String ACTION_AUTO_UPDATE = "AUTO_UPDATE";

    public static final int REQUEST_WORDS = 1;


    @Override
    public void onReceive(final Context context, Intent i) {
        super.onReceive(context, i);

        int code = i.getIntExtra("code", -1);
        if (code == -1) {
            updateUI(context);
        } else {
            updateWords(context);
        }

        System.out.println("onReceive " + i.getAction());
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        this.updateUI(context);
    }


    private void updateUI(Context context) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        final ComponentName componentName = new ComponentName(context, AppWidget.class);


        ViewInfo info = ViewInfo.getInstance(context);
        remoteViews.setTextViewText(R.id.widget_target_title, info.getTargetTitle());
        remoteViews.setTextViewText(R.id.widget_target_year, info.getTargetDate().substring(0, 4));
        remoteViews.setTextViewText(R.id.widget_target_month, info.getTargetDate().substring(4, 7));
        remoteViews.setTextViewText(R.id.widget_target_date, info.getTargetDate().substring(7, 10));
        remoteViews.setTextViewText(R.id.widget_days, info.calcDays() + "");
        remoteViews.setTextViewText(R.id.widget_words, info.randomAphorism());


        Intent intent = new Intent(ACTION_AUTO_UPDATE);
        intent.putExtra("code", REQUEST_WORDS);
        intent.setClass(context.getApplicationContext(), this.getClass());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_WORDS, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_words, pendingIntent);

        appWidgetManager.updateAppWidget(componentName, remoteViews);
    }

    private void updateWords(Context context) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        final ComponentName componentName = new ComponentName(context, AppWidget.class);


        ViewInfo info = ViewInfo.getInstance(context);
        remoteViews.setTextViewText(R.id.widget_words, info.randomAphorism());

        appWidgetManager.updateAppWidget(componentName, remoteViews);
    }
}
