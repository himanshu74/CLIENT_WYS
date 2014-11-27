package wys.Background;

import wys.ForumObjects.CategoryActivity;
import wys.FrontLayer.MainActivity;
import wys.ORG.OrgHomeActivity;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.wys.R;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class GcmMessageHandler extends IntentService {

	String mes;
	private Handler handler;

	public GcmMessageHandler() {
		super("GcmMessageHandler");

	}

	@Override
	public void onCreate() {

		super.onCreate();
		handler = new Handler();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);
        String data = intent.getStringExtra("alert");
		String type=intent.getStringExtra("type");
         generateNotification(getApplicationContext(), data);
        GcmBroadcastReceiver.completeWakefulIntent(intent);
	}
	
	
	 public static void generateNotification(Context context, String message) {
	        int icon = R.drawable.ic_launcher;
	        long when = System.currentTimeMillis();
	        NotificationManager notificationManager = (NotificationManager)
	                context.getSystemService(Context.NOTIFICATION_SERVICE);
	        Notification notification = new Notification(icon, message, when);
	         
	        String title = context.getString(R.string.app_name);
	         
	        Intent notificationIntent = new Intent(context, CategoryActivity.class);
	        // set intent so it does not start a new activity
	        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
	                Intent.FLAG_ACTIVITY_SINGLE_TOP);
	        PendingIntent intent =
	                PendingIntent.getActivity(context, 0, notificationIntent, 0);
	        notification.setLatestEventInfo(context, title, message, intent);
	        notification.flags |= Notification.FLAG_AUTO_CANCEL;
	         
	        // Play default notification sound
	        notification.defaults |= Notification.DEFAULT_SOUND;
	         
	        // Vibrate if vibrate is enabled
	        notification.defaults |= Notification.DEFAULT_VIBRATE;
	        notificationManager.notify(0, notification);      
	 
	    }
	

}
