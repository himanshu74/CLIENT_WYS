package wys.AsyncTask;

import java.io.IOException;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.wys.R;

import wys.Api.WysApi;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;

public class NotificationRegister {

	private Context _ctx;

	public NotificationRegister(Context context) {
		this._ctx = context;
		new NotificationRegisterAsync().execute();
	}

	private class NotificationRegisterAsync extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(_ctx.getApplicationContext());
			String regId = null;
			try {
				regId = gcm.register(_ctx.getResources().getString(
						R.string.sender_id));
			} catch (NotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			new WysApi().doRegisterGcm(regId);
			return null;

		
		}
	}

}
