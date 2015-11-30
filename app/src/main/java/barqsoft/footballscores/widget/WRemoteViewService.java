package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

/**
 * Created by Kamini on 11/28/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WRemoteViewService  extends RemoteViewsService {
    @Override

        public RemoteViewsFactory onGetViewFactory(Intent intent) {
           return new StackRemoteViewsFactory(this.getApplicationContext(), intent);

       // return new StackRemoteViewsFactory()
        }

}

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final int mCount = 10;
   // private List<WidgetItem> mWidgetItems = new ArrayList<WidgetItem>();
    private Context mContext;
    private int mAppWidgetId;

    public final String LOG_TAG = WidgetRemoteViewsService.class.getSimpleName();
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
    private Cursor data = null;

    public StackRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    public void onCreate() {

    }

    public void onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your
        // data source,
        // eg. cursors, connections, etc.
      //  mWidgetItems.clear();
        if (data != null) {
            data.close();
            data = null;
        }
    }

    public int getCount() {
      //  return data.getCount();
        return data == null ? 0 : data.getCount();
    }

    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                data == null || !data.moveToPosition(position)) {
            Log.e(LOG_TAG, "cursor error");
            return null;
        }
        RemoteViews views = new RemoteViews(mContext. getPackageName(),
                R.layout.widget_today);



        Intent fillInIntent = new Intent();
        fillInIntent.putExtra("EXTRA_ITEM", position);
        views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
        int matchId = data.getInt(INDEX_MATCH_ID);
        int matchHomeIcon = (Utilies.getTeamCrestByTeamName(
                data.getString(INDEX_HOME_COL)));
        String descriptionHome = data.getString(INDEX_HOME_COL);

        int matchAwayIcon = (Utilies.getTeamCrestByTeamName(
                data.getString(INDEX_AWAY_COL)));
        String descriptionAway = data.getString(INDEX_AWAY_COL);
        String matchTime = data.getString(INDEX_TIME_COL);


        //    views.setImageViewResource(R.id.widget_home_crest, matchHomeIcon);
        //  views.setImageViewResource(R.id.widget_away_crest, matchAwayIcon);
        // Content Descriptions for RemoteViews were only added in ICS MR1

        views.setTextViewText(R.id.widget_home_name, descriptionHome);

        views.setTextViewText(R.id.widget_time, matchTime);
        views.setTextViewText(R.id.wiget_away_name, descriptionAway);
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            setRemoteContentDescription(views, descriptionHome);
        }*/


       /* final Intent fillInIntent = new Intent();

        Uri dateuri=  DatabaseContract.scores_table.buildScoreWithDate();
        fillInIntent.setData(dateuri);
        views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);*/
        return views;

    }

    public RemoteViews getLoadingView() {
        // You can create a custom loading view (for instance when getViewAt()
        // is slow.) If you
        // return null here, you will get the default loading view.

        return new RemoteViews(mContext.getPackageName(), R.layout.widget_today);
      // return null;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public long getItemId(int position) {

        if (data.moveToPosition(position)){
            return data.getLong(INDEX_MATCH_ID);
        }

        return position;
    }

    public boolean hasStableIds() {
        return true;
    }

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
        Uri dateuri=  DatabaseContract.scores_table.buildScoreWithDate();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dateobj = new Date();
        // Date tomorrow = new Date(dateobj.getTime() +(1000 * 60 * 60 * 24));
        // dateobj.plusDays(1)
        // dateobj
        Cursor data =
               mContext. getContentResolver().query(dateuri, MATCH_COLUMNS, null,
                        new String[]{df.format(dateobj)}, DatabaseContract.scores_table.DATE_COL + " ASC");
        Log.e(LOG_TAG, "datacount  : " + data.getCount());
        Binder.restoreCallingIdentity(identityToken);
    }
}