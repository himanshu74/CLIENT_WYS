package wys.AlarmService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

	private int topic_id;

	@Override
	public void onReceive(Context context, Intent intent) {
		topic_id = intent.getIntExtra("topic_id", -1);
		CommentDownloadAlarm.TOPIC_ID = topic_id;
		Intent i = new Intent(context.getApplicationContext(),
				CommentDownloadAlarm.class);
		context.startService(i);
	}

}
