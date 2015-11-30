package barqsoft.footballscores.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.Toast;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

/**
 * Created by Kamini on 11/28/2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WProvider extends AppWidgetProvider {

    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; ++i) {
            // This intent will start the CollectionWidgetService
            // this intent will have the appwidgetId for an extra
            Intent intent = new Intent(context, WRemoteViewService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            // Intent clickingIntent = new Intent(context.getApplicationContext(), MainActivity.class);
            // PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, clickingIntent, 0);
            // RemoteViews object for the collection widget's layout
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.widget_detail );

            Intent startMainActivity = new Intent(context, MainActivity.class);
            PendingIntent startMainActivityPending = PendingIntent.getActivity(context,
                    0, startMainActivity, PendingIntent.FLAG_UPDATE_CURRENT );
            remoteViews.setPendingIntentTemplate(R.id.widget_list, startMainActivityPending);

            // set up the pending intent to launch main activity
            //remoteViews.setOnClickPendingIntent(R.id.collectionListViewId, pendingIntent);
            // Now connect the remoteviews object to use a remoteviews adapter.
            // this connects to a RemoteViewsService, described by the intent above.
            remoteViews.setRemoteAdapter(R.id.widget_list, intent);
            // set the empty view
            remoteViews.setEmptyView(R.id.widget_list, R.id.widget_empty);

            appWidgetManager.updateAppWidget(appWidgetIds[i],remoteViews);
        }
    }


}
