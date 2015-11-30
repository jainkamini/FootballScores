package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.service.myFetchService;

/**
 * Provider for a widget showing today's weather.
 *
 * Delegates widget updating to {@link TodayWidgetIntentService} to ensure that
 * data retrieval is done on a background thread
 */
public class TodayWidgetProvider extends AppWidgetProvider {

    public static final String LOG_TAG = "TodayWidgetProvider";
    private static final String[] MATCH_COLUMNS = {
            DatabaseContract.scores_table.MATCH_ID,
            DatabaseContract.scores_table.LEAGUE_COL,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.TIME_COL
    };
    // these indices must match the projection
    private static final int INDEX_MATCH_ID = 0;
    private static final int INDEX_LEAGUE_COL = 1;
    private static final int INDEX_HOME_COL = 2;
    private static final int INDEX_AWAY_COL = 3;
    private static final int INDEX_DATE_COL = 4;
    private static final int INDEX_TIME_COL = 5;


     @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
         context.startService(new Intent(context, TodayWidgetIntentService.class));
     }

  //  Context context;

    /*@Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


       // context.startService(new Intent(context, TodayWidgetIntentService.class));


       myFetchService fetchService=new myFetchService();
       // fetchService.getData("n2");
      // fetchService. getData("p2");
        Uri dateuri=  DatabaseContract.scores_table.buildScoreWithDate();


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date();
        Date tomorrow = new Date(dateobj.getTime() - (1000 * 60 * 60 * 24));
       // dateobj.plusDays(1)
       // dateobj
        Cursor data =
               context. getContentResolver().query(dateuri, MATCH_COLUMNS, null,
                new String[] { df.format(tomorrow)}, DatabaseContract.scores_table.DATE_COL +  " ASC");
        if (data == null) {
            return;
        }
        if (!data.moveToFirst()) {
            data.close();
            return;
        }

        // Extract the weather data from the Cursor


      *//* // int matchId = data.getInt(INDEX_MATCH_ID);
        int matchHomeIcon =R.drawable.no_icon;
        String descriptionHome = "hjdhjhdfhdjkshkfjd";
      //  descriptionHome="hello";
        int matchAwayIcon =R.drawable.no_icon;
        String descriptionAway ="hjcjsjhdjshjdhs";
        String matchTime ="8:30";*//*

       int matchId = data.getInt(INDEX_MATCH_ID);
        int matchHomeIcon =(Utilies.getTeamCrestByTeamName(
                data.getString(INDEX_HOME_COL)));
        String descriptionHome = data.getString(INDEX_HOME_COL);

        int matchAwayIcon =(Utilies.getTeamCrestByTeamName(
                data.getString(INDEX_AWAY_COL)));
        String descriptionAway = data.getString(INDEX_AWAY_COL);
      String matchTime =data.getString(INDEX_TIME_COL);
       // data.close();

        // Perform this loop procedure for each Today widget
        for (int appWidgetId : appWidgetIds) {
            int widgetWidth = getWidgetWidth(appWidgetManager, appWidgetId,context);

        //  int  layoutId = R.layout.widget_today;
            int defaultWidth = context. getResources().getDimensionPixelSize(R.dimen.widget_today_default_width);
            int largeWidth = context.getResources().getDimensionPixelSize(R.dimen.widget_today_large_width);
            int layoutId;
           *//* if (widgetWidth >= largeWidth) {
                layoutId = R.layout.widget_today_large;
            } else if (widgetWidth >= defaultWidth) {
                layoutId = R.layout.widget_today;
            } else {
                layoutId = R.layout.widget_today_small;
            }*//*

            if (widgetWidth >= largeWidth) {
                layoutId = R.layout.widget_today_large;
            } else  {
                layoutId = R.layout.widget_today;

            }
            RemoteViews views = new RemoteViews(context.getPackageName(), layoutId);

            // Add the data to the RemoteViews
            views.setImageViewResource(R.id.widget_home_crest, matchHomeIcon);
            views.setImageViewResource(R.id.widget_away_crest, matchAwayIcon);
            // Content Descriptions for RemoteViews were only added in ICS MR1

            views.setTextViewText(R.id.widget_home_name, descriptionHome);

            views.setTextViewText(R.id.widget_time, matchTime);
            views.setTextViewText(R.id.wiget_away_name, descriptionAway);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                setRemoteContentDescription(views, descriptionHome);
            }
            // Create an Intent to launch MainActivity
           Intent launchIntent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
            // updateWidgets();
            // Log.e(TodayWidgetProvider.LOG_TAG, "I am inside here");
        }
    }
*/

    private int getWidgetWidth(AppWidgetManager appWidgetManager, int appWidgetId,Context context) {
        // Prior to Jelly Bean, widgets were always their default size
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return context. getResources().getDimensionPixelSize(R.dimen.widget_today_default_width);
        }
        // For Jelly Bean and higher devices, widgets can be resized - the current size can be
        // retrieved from the newly added App Widget Options
        return getWidgetWidthFromOptions(appWidgetManager, appWidgetId,context);
    }
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void setRemoteContentDescription(RemoteViews views, String description) {
        views.setContentDescription(R.id.widget_home_crest, description);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private int getWidgetWidthFromOptions(AppWidgetManager appWidgetManager, int appWidgetId,Context context) {
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        if (options.containsKey(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)) {
            int minWidthDp = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
            // The width returned is in dp, but we'll convert it to pixels to match the other widths
            DisplayMetrics displayMetrics =context. getResources().getDisplayMetrics();
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, minWidthDp,
                    displayMetrics);
        }
        return context. getResources().getDimensionPixelSize(R.dimen.widget_today_default_width);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                     int appWidgetId, Bundle newOptions) {
       // context.startService(new Intent(context, TodayWidgetIntentService.class));
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        super.onReceive(context, intent);
       // myFetchService myFetchservice = null;
      /*if (TodayWidgetIntentService.ACTION_DATA_UPDATED.equals(intent.getAction())) {
            context.startService(new Intent(context, TodayWidgetIntentService.class));
        }*/
    }
}