package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.lang.annotation.Target;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

/**
 * Created by Kamini on 11/24/2015.
 */
/**
 * RemoteViewsService controlling the data being shown in the scrollable weather detail widget
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WidgetRemoteViewsService extends RemoteViewsService {
    public final String LOG_TAG = WidgetRemoteViewsService.class.getSimpleName();
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
   // Context mcontext=  getApplicationContext();
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;


            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();
                //  String location = Utility.getPreferredLocation(DetailWidgetRemoteViewsService.this);
              //  Uri dateuri = DatabaseContract.scores_table.buildScoreWithDate();
                Uri dateuri = DatabaseContract.scores_table.buildScoreWithDateGrater();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date dateobj = new Date();
               // Date tomorrow = new Date(dateobj.getTime() - (1000 * 60 * 60 * 48));
                // dateobj.plusDays(1)
                // dateobj
                /*data =
                        getContentResolver().query(dateuri, MATCH_COLUMNS, null,
                                new String[]{df.format(dateobj)}, DatabaseContract.scores_table.DATE_COL + " ASC");*/
                data =
                        getContentResolver().query(dateuri, MATCH_COLUMNS, null,
                                new String[]{df.format(dateobj)}, DatabaseContract.scores_table.DATE_COL + " ASC");
                Log.e(LOG_TAG, "datacount  : " + data.getCount());
                // Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {

                // return data.getCount();
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {

       /* RemoteViews mView = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);*/

                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    Log.e(LOG_TAG, "cursor error");
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_collection_item);
                //  if (data.moveToPosition(position) && data !=null) {

                Log.e(LOG_TAG, "datacountremote  : " + data.getCount());
                //  mView.setTextViewText(android.R.id.text1, mCollections.get(position).toString());

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
int mPosition=-1;


                views.setTextViewText(R.id.widget_home_name, descriptionHome);


                views.setTextViewText(R.id.widget_time, matchTime);

                    views.setTextViewText(R.id.score_textview, Utilies.getScores(homeGoles, awayGoles,getApplicationContext()));


                views.setTextViewText(R.id.wiget_away_name, descriptionAway);
                views.setImageViewResource(R.id.home_crest, Utilies.getTeamCrestByTeamName(
                        data.getString(INDEX_HOME_COL)));
                views.setImageViewResource(R.id.away_crest,Utilies.getTeamCrestByTeamName(
                        data.getString(INDEX_AWAY_COL)));

                //  mView.setTextColor(android.R.id.text1, Color.BLACK);

           /* final Intent fillInIntent = new Intent();
            fillInIntent.setAction(WidgetProvider.ACTION_TOAST);
            final Bundle bundle = new Bundle();
            bundle.putString(WidgetProvider.EXTRA_STRING,
                    data.getString(INDEX_MATCH_ID));
            fillInIntent.putExtras(bundle);
            views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);*/
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date dateobj = new Date();
                Date tomorrow = new Date(dateobj.getTime() + (1000 * 60 * 60 * 24));
                Date tomorrowafter = new Date(dateobj.getTime() +(1000 * 60 * 60 * 48));


                if (matchdate.compareTo(df.format(dateobj)) == 0) {
                    mfragment = 2;
                    Log.e(LOG_TAG, "datacountremote  : " + mfragment);
                } else if (matchdate.compareTo(df.format(tomorrow)) == 0) {
                    mfragment = 3;
                    Log.e(LOG_TAG, "datacountremote  : " + mfragment);
                } else if (matchdate.compareTo(df.format(tomorrowafter)) == 0) {
                    mfragment =4 ;
                    Log.e(LOG_TAG, "datacountremote  : " + mfragment);
                }
                Log.e(LOG_TAG, "Date  : " + matchdate + "," + df.format(dateobj));
                Log.e(LOG_TAG, "fragmateno in provider   : " + mfragment);
             //   Log.e(LOG_TAG, "Postion of item   : " + position);

                for (int i =0 ;i<=position;i++)
                {
                    data.moveToPosition(i);
                    if (data.getString(INDEX_DATE_COL).compareTo(df.format(dateobj)) == 0) {
                        mPosition++;
                    } else if (data.getString(INDEX_DATE_COL).compareTo(df.format(tomorrow)) == 0) {
                        mPosition++;
                        Log.e(LOG_TAG, "datacountremote  : " + mfragment);
                    } else if (data.getString(INDEX_DATE_COL).compareTo(df.format(tomorrowafter)) == 0) {
                        mPosition++ ;
                        Log.e(LOG_TAG, "datacountremote  : " + mfragment);
                    }

                    if (matchId == data.getInt(INDEX_MATCH_ID))
                    {
                        break ;
                    }
                }
                Log.e(LOG_TAG, "Postion of item   : " + mPosition);

                final Intent fillInIntent = new Intent();
                fillInIntent.setAction(WidgetIntentProvider.ACTION_TOAST);
                final Bundle bundle = new Bundle();
                bundle.putInt(WidgetIntentProvider.EXTRA_FRAGMENT,
                        mfragment);
                bundle.putInt(WidgetIntentProvider.EXTRA_MATCHID,
                        matchId);
                bundle.putInt(WidgetIntentProvider.EXTRA_POSITION ,
                        mPosition);


                fillInIntent.putExtras(bundle);
                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);

                //  }
                return views;
            }


            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            private void setRemoteContentDescription(RemoteViews views, String description) {
                // views.setContentDescription(R.id.widget_icon, description);
            }

            @Override
            public RemoteViews getLoadingView() {
                // return null;
                return new RemoteViews(getPackageName(), R.layout.widget_collection_item);
            }

            @Override
            public int getViewTypeCount() {

                return 1;
            }

            @Override
            public long getItemId(int position) {

                if (data.moveToPosition(position))
                    return data.getLong(INDEX_MATCH_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}