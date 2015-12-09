package barqsoft.footballscores.widget;

/**
 * Created by Kamini on 11/20/2015.
 */
import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.R;
import barqsoft.footballscores.ScoresProvider;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.ScoresProvider;
import barqsoft.footballscores.service.myFetchService;

/**
 * IntentService which handles updating all Today widgets with the latest data
 */
public class TodayWidgetIntentService extends IntentService {
    private static final String[] MATCH_COLUMNS = {

            DatabaseContract.scores_table.MATCH_ID,
            DatabaseContract.scores_table.LEAGUE_COL,
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.DATE_COL,
            DatabaseContract.scores_table.TIME_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL

    };
    // these indices must match the projection
    private static final int INDEX_MATCH_ID = 0;
    private static final int INDEX_LEAGUE_COL = 1;
    private static final int INDEX_HOME_COL = 2;
    private static final int INDEX_AWAY_COL = 3;
    private static final int INDEX_DATE_COL = 4;
    private static final int INDEX_TIME_COL = 5;
    private static final int INDEX_HOMEGOALS_COL = 6;
    private static final int INDEX_AWAYGOALS_COL = 7;
    public static final String ACTION_TOAST = " barqsoft.footballscores.ACTION_TOAST";

    public TodayWidgetIntentService() {
        super("TodayWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Retrieve all of the Today widget ids: these are the widgets we need to update
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                TodayWidgetProvider.class));

        myFetchService fetchService = new myFetchService();
       // fetchService.getDaUri dateuri = DatabaseContract.scores_table.buildScoreWithDate();
        Uri dateuri=  DatabaseContract.scores_table.buildScoreWithDate();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date();
        Date tomorrow = new Date(dateobj.getTime() - (1000 * 60 * 60 * 24));
        // dateobj.plusDays(1)
        // dateobj
        Cursor data =
                getContentResolver().query(dateuri, MATCH_COLUMNS, null,
                        new String[]{df.format(dateobj)}, DatabaseContract.scores_table.DATE_COL + " ASC");
      //  fetchService.getData("p2");

        if (data == null) {
            return;
        }
        if (!data.moveToFirst()) {
            data.close();
            return;
        }


        int matchId = data.getInt(INDEX_MATCH_ID);
        int matchHomeIcon = (Utilies.getTeamCrestByTeamName(
                data.getString(INDEX_HOME_COL)));
        String descriptionHome = data.getString(INDEX_HOME_COL);

        int matchAwayIcon = (Utilies.getTeamCrestByTeamName(
                data.getString(INDEX_AWAY_COL)));
        String descriptionAway = data.getString(INDEX_AWAY_COL);
        String matchTime = data.getString(INDEX_TIME_COL);
        int homeGoles = data.getInt(INDEX_HOMEGOALS_COL);
        int awayGoles = data.getInt(INDEX_AWAYGOALS_COL);
        String matchdate = data.getString(INDEX_DATE_COL);

        String mGoles = "No Scores";

        int mfragment = 2;
        Log.e(TodayWidgetProvider.LOG_TAG, "I am inside here");
        // Perform this loop procedure for each Today widget
        for (int appWidgetId : appWidgetIds) {
            int widgetWidth = getWidgetWidth(appWidgetManager, appWidgetId);

            //  int  layoutId = R.layout.widget_today;
            //int defaultWidth = getResources().getDimensionPixelSize(R.dimen.widget_today_default_width);
            //int largeWidth = getResources().getDimensionPixelSize(R.dimen.widget_today_large_width);
            int layoutId;
           /* if (widgetWidth >= largeWidth) {
                layoutId = R.layout.widget_today_large;
            } else if (widgetWidth >= defaultWidth) {
                layoutId = R.layout.widget_today;
            } else {
                layoutId = R.layout.widget_today_small;
            }*/


            layoutId = R.layout.widget_today;
            RemoteViews views = new RemoteViews(getPackageName(), layoutId);

            views.setTextViewText(R.id.widget_home_name, descriptionHome);


            views.setTextViewText(R.id.widget_time, matchTime);

            views.setTextViewText(R.id.score_textview, Utilies.getScores(homeGoles, awayGoles,getApplicationContext()));


            views.setTextViewText(R.id.wiget_away_name, descriptionAway);
            views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(
                    data.getString(INDEX_HOME_COL)));
            views.setImageViewResource(R.id.away_crest, Utilies.getTeamCrestByTeamName(
                    data.getString(INDEX_AWAY_COL)));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                setRemoteContentDescription(views, descriptionHome);
            }
            // Create an Intent to launch MainActivity
           // Intent launchIntent = new Intent(this, MainActivity.class);
           // PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
           // views.setOnClickPendingIntent(R.id.widget, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            Intent clickingIntent = new Intent(getApplicationContext(), MainActivity.class);

            final Bundle bundle = new Bundle();
            bundle.putInt(WidgetIntentProvider.EXTRA_FRAGMENT,
                    2);
            bundle.putInt(WidgetIntentProvider.EXTRA_MATCHID,
                    matchId);
            bundle.putInt(WidgetIntentProvider.EXTRA_POSITION,
                    0);

            clickingIntent.setAction(WidgetProvider.ACTION_TOAST);
            clickingIntent.putExtras(bundle);
            clickingIntent.setData(Uri.parse(clickingIntent
                    .toUri(Intent.URI_INTENT_SCHEME)));


            final PendingIntent onClickPendingIntent = TaskStackBuilder.create(getApplicationContext())
                    .addNextIntentWithParentStack(clickingIntent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget, onClickPendingIntent);


           /* final Intent fillInIntent = new Intent();
            fillInIntent.setAction(WidgetProvider.ACTION_TOAST);
            final Bundle bundle = new Bundle();
            bundle.putInt(WidgetIntentProvider.EXTRA_FRAGMENT,
                    mfragment);
            bundle.putInt(WidgetIntentProvider.EXTRA_MATCHID,
                    matchId);
            bundle.putInt(WidgetIntentProvider.EXTRA_POSITION ,
                    0 );


            fillInIntent.putExtras(bundle);
            views.setOnClickFillInIntent(R.id.widget, fillInIntent);*/
            appWidgetManager.updateAppWidget(appWidgetId, views);
            // updateWidgets();
            // Log.e(TodayWidgetProvider.LOG_TAG, "I am inside here");

        }

    }
    private int getWidgetWidth(AppWidgetManager appWidgetManager, int appWidgetId) {
        // Prior to Jelly Bean, widgets were always their default size
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            return getResources().getDimensionPixelSize(R.dimen.widget_today_default_width);
        }
        // For Jelly Bean and higher devices, widgets can be resized - the current size can be
        // retrieved from the newly added App Widget Options
        return getWidgetWidthFromOptions(appWidgetManager, appWidgetId);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private int getWidgetWidthFromOptions(AppWidgetManager appWidgetManager, int appWidgetId) {
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        if (options.containsKey(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH)) {
            int minWidthDp = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
            // The width returned is in dp, but we'll convert it to pixels to match the other widths
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, minWidthDp,
                    displayMetrics);
        }
        return  getResources().getDimensionPixelSize(R.dimen.widget_today_default_width);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void setRemoteContentDescription(RemoteViews views, String description) {
      //  views.setContentDescription(R.id.widget_icon, description);
    }
}