package barqsoft.footballscores.widget;

/**
 * Created by Kamini on 11/30/2015.
 */
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

public class WidgetProvider extends AppWidgetProvider {
    public static final String ACTION_TOAST = " barqsoft.footballscores.ACTION_TOAST";
    public static final String EXTRA_MATCHID = " barqsoft.footballscores.EXTRA_MATCHID";
    public static final String EXTRA_FRAGMENT = " barqsoft.footballscores.EXTRA_FRAGMENT";
    public static final String USER_PRESENT = " barqsoft.footballscores.USER_PRESENT";
    public final String LOG_TAG = WidgetProvider.class.getSimpleName();

    @SuppressLint("NewApi")
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            RemoteViews mView = initViews(context, appWidgetManager, widgetId);

          final Intent onItemClick = new Intent(context, MainActivity.class);
            onItemClick.setAction(ACTION_TOAST);
            onItemClick.setData(Uri.parse(onItemClick
                    .toUri(Intent.URI_INTENT_SCHEME)));


            final PendingIntent onClickPendingIntent = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(onItemClick)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mView.setPendingIntentTemplate(R.id.widget_list,
                    onClickPendingIntent);


            /*  boolean useDetailActivity = context.getResources()
                                      .getBoolean(R.bool.use_detail_activity);
                      Intent clickIntentTemplate = useDetailActivity
                                       ? new Intent(context, DetailActivity.class)
                                        : new Intent(context, MainActivity.class);
                       PendingIntent clickPendingIntentTemplate = TaskStackBuilder.create(context)
                                       .addNextIntentWithParentStack(clickIntentTemplate)
                                        .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                      views.setPendingIntentTemplate(R.id.widget_list, clickPendingIntentTemplate);*/

            // Adding collection list item handler
           /* final Intent onItemClick = new Intent(context, WidgetProvider.class);
            onItemClick.setAction(ACTION_TOAST);
            onItemClick.setData(Uri.parse(onItemClick
                    .toUri(Intent.URI_INTENT_SCHEME)));
            final PendingIntent onClickPendingIntent = PendingIntent
                    .getBroadcast(context, 0, onItemClick,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            mView.setPendingIntentTemplate(R.id.widget_list,
                    onClickPendingIntent);*/

          /* Intent intentDelayed = new Intent(context, WidgetDataProvider.class);
            intentDelayed.setAction(ACTION_REFRESH_DAY);



            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                    intentDelayed, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager almMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            almMgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, pendingIntent);*/

           /* Intent startMainActivity = new Intent(context, MainActivity.class);
           *//* B

            Log.e(LOG_TAG, "MatchId  : " + bundle.getString(WidgetProvider.EXTRA_STRING));
            startMainActivity.putExtras(bundle);*//*
         //   startMainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          // startMainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startMainActivity.setData(Uri.parse(startMainActivity.toUri(Intent.URI_INTENT_SCHEME)));

            PendingIntent startMainActivityPending = PendingIntent.getActivity(context,
                    0, startMainActivity, PendingIntent.FLAG_UPDATE_CURRENT);
            mView.setPendingIntentTemplate(R.id.widget_list, startMainActivityPending);*/
           /* mView.setPendingIntentTemplate(R.id.widgetCollectionList,
                    onClickPendingIntent);*/
           /* mView.setPendingIntentTemplate(R.id.widget_list,
                    onClickPendingIntent);*/

            appWidgetManager.updateAppWidget(widgetId, mView);
        }


        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_TOAST)) {
            int item = intent.getExtras().getInt(EXTRA_MATCHID);
           int item1 = intent.getExtras().getInt(EXTRA_FRAGMENT);
           // Toast.makeText(context, item, Toast.LENGTH_LONG).show();
          //  Toast.makeText(context, item1, Toast.LENGTH_LONG).show();
          //  intent.getExtras();
        }
        super.onReceive(context, intent);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private RemoteViews initViews(Context context,
                                  AppWidgetManager widgetManager, int widgetId) {

        RemoteViews mView = new RemoteViews(context.getPackageName(),
                R.layout.widget_detail);

        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);

        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));


        mView.setRemoteAdapter(widgetId, R.id.widget_list, intent);
        // set the empty view
        mView.setEmptyView(R.id.widget_list, R.id.widget_empty);

        return mView;
    }


    /*private void requestDelayedWidgetUpdate(Context context, int appWidgetId){

        Intent intentDelayed = new Intent(context, FootballScoresAppWidget.class);
        intentDelayed.setAction(ACTION_REFRESH_DAY);



        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intentDelayed, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager almMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        almMgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, pendingIntent);


    }*/
}
