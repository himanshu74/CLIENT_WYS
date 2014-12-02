package wys.AlarmService;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import wys.Base.BaseDbActivity;

public class WysAlarmActivity extends BaseDbActivity {

	private Context _ctx = WysAlarmActivity.this;
	private int interval = 10*60*1000;
	PendingIntent pendingIntent;
	private int topic_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        topic_id = getIntent().getIntExtra("topic_id", -1);
		setUpAlarm();
		StartAlarm();

		super.onCreate(savedInstanceState);
	}

	private void setUpAlarm() {
		Intent alarmIntent = new Intent(_ctx, AlarmReceiver.class);
		alarmIntent.putExtra("topic_id", topic_id);
		pendingIntent = PendingIntent.getBroadcast(_ctx, 0, alarmIntent, 0);
	}

	public void StartAlarm() {

		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), interval, pendingIntent);
		Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
	}

	public void cancelWysAlarm() {
		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		manager.cancel(pendingIntent);
		Toast.makeText(this, "Alarm Canceled", Toast.LENGTH_SHORT).show();
	}

}
