package barqsoft.footballscores.widget;

/**
 * Created by Kamini on 11/30/2015.
 */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

@SuppressLint("NewApi")
public class WidgetDataProvider implements RemoteViewsFactory {
    public final String LOG_TAG = WidgetDataProvider.class.getSimpleName();
    List mCollections = new ArrayList();

   Context mContext ;//= null;

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
    private Cursor data = null;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public int getCount() {
       return data == null ? 0 : data.getCount();

     //  return data.getCount();
    }

    @Override
    public long getItemId(int position) {

        if (data.moveToPosition(position)){
            return data.getLong(INDEX_MATCH_ID);
        }
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
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
        RemoteViews views = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_today);
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
        int homeGoles=data.getInt(INDEX_HOMEGOALS_COL);
        int awayGoles=data.getInt(INDEX_AWAYGOALS_COL);
        String matchdate=data.getString(INDEX_DATE_COL);

        String mGoles="No Scores";

int mfragment=2;



        mGoles=  Utilies.getScores(homeGoles,awayGoles);
            views.setTextViewText(R.id.widget_home_name, descriptionHome);

        if ("No Scores".equals(mGoles)||  " - " .equals(mGoles)) {
            views.setTextViewText(R.id.widget_time, matchTime);
        }
        else {
            views.setTextViewText(R.id.widget_time, mGoles);
        }
            views.setTextViewText(R.id.wiget_away_name, descriptionAway);

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
        Date tomorrow = new Date(dateobj.getTime() +(1000 * 60 * 60 * 24));
        Date tomorrowafter = new Date(dateobj.getTime() +(1000 * 60 * 60 * 48));


        if ( matchdate.compareTo(df.format(dateobj))==0)
        {
            mfragment=2;
            Log.e(LOG_TAG, "datacountremote  : " + mfragment);
        }
        else if ( matchdate.compareTo(df.format(tomorrow))==0)
        {
            mfragment=3;
            Log.e(LOG_TAG, "datacountremote  : " + mfragment);
        }
        else if ( matchdate.compareTo(df.format(tomorrowafter))==0)
        {
            mfragment=4;
            Log.e(LOG_TAG, "datacountremote  : " + mfragment);
        }
        Log.e(LOG_TAG, "Date  : " + matchdate +","+df.format(dateobj));
        Log.e(LOG_TAG, "fragmateno in provider   : " + mfragment);

        final Intent fillInIntent = new Intent();
        fillInIntent.setAction(WidgetProvider.ACTION_TOAST);
        final Bundle bundle = new Bundle();
        bundle.putInt(WidgetProvider.EXTRA_FRAGMENT,
                mfragment);
        bundle.putInt(WidgetProvider.EXTRA_MATCHID,
                data.getInt(INDEX_MATCH_ID));

        fillInIntent.putExtras(bundle);
        views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);

      //  }
        return views;
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
     //   initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    private void initData() {
       /* mCollections.clear();
        for (int i = 1; i <= 10; i++) {
            mCollections.add("ListView item " + i);
        }*/

        if (data != null) {
            data.close();
        }
     //   final long identityToken = Binder.clearCallingIdentity();
        Uri dateuri=  DatabaseContract.scores_table.buildScoreWithDateGrater();
       // Uri dateuri=  DatabaseContract.scores_table.buildScoreWithDate();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date();

       Date tomorrow = new Date(dateobj.getTime() +(1000 * 60 * 60 * 24));
      //  String wherecls = "DatabaseContract.scores_table.DATE_COL <?  ";

         /*data =
          mContext.      getContentResolver().query(dateuri, MATCH_COLUMNS, null,
                        new String[]{df.format(tomorrow) }, DatabaseContract.scores_table.DATE_COL + " ASC");*/
        data =
                mContext.      getContentResolver().query(dateuri, MATCH_COLUMNS,  null,
                        new String[]{df.format(dateobj) } ,DatabaseContract.scores_table.DATE_COL + " ASC") ;




       /*data =
                mContext.      getContentResolver().query(dateuri, MATCH_COLUMNS, null,
                        new String[]{df.format(tomorrow) + "  = " + DatabaseContract.scores_table.DATE_COL } ,null);*/

        Log.e(LOG_TAG, "datacountremote  : " + data.getCount());


     //  Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
        if (data != null) {
            data.close();
            data = null;
        }
    }

}

