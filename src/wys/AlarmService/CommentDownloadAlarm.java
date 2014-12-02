package wys.AlarmService;

import java.util.ArrayList;

import wys.Api.WysUserApi;
import wys.Background.CommentsDownloadService;
import wys.BroadcastReceivers.WysBroadcastConstants;
import wys.Business.CommentBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.PreferenceHelper;
import wys.Modals.CommentModal;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

public class CommentDownloadAlarm extends IntentService {

	boolean status = false;
	public static DBAdapter dbAdapter;
	public CommentDownloadAlarm() {
		super("CommentDownloadAlarm");
	}

	public static int TOPIC_ID;

	@Override
	protected void onHandleIntent(Intent intent) {

		Context ctx = CommentDownloadAlarm.this;
		ArrayList<CommentBo> list;
		PreferenceHelper prefs = new PreferenceHelper(ctx);
		int serverid = prefs.get_commentsServerId();
		if (serverid != -1) {
			WysUserApi wysUserApi = new WysUserApi();
			list = wysUserApi.getLatestComments(TOPIC_ID, serverid);
		} else {
			WysUserApi wysUserApi = new WysUserApi();
			list = wysUserApi.getcommentsBytopic(TOPIC_ID);
		}
		
		if (list != null && list.size() > 0) {

			if (prefs.get_commentsServerId() != list.get(0).get_serverId()) {
				prefs.set_commentsServerId(list.get(0).get_serverId());
				for (CommentBo comment : list) {
					status = CommentModal.saveComment(dbAdapter.getDb(),
							comment);
				}
			}

		}
		dbAdapter=null;
		sendNotification(status);
	}
	
	private void sendNotification(boolean status) {
		if (status) {
			Intent broadCastIntent = new Intent();
			broadCastIntent
					.setAction(WysBroadcastConstants.COMMENTS_RECEIVED_ACTION);
			broadCastIntent.putExtra("Status", 0);
			broadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);
			sendBroadcast(broadCastIntent);
		} else {
			Intent broadCastIntent = new Intent();
			broadCastIntent
					.setAction(WysBroadcastConstants.COMMENTS_RECEIVED_ACTION);
			broadCastIntent.putExtra("Status", 1);
			broadCastIntent.addCategory(Intent.CATEGORY_DEFAULT);
			sendBroadcast(broadCastIntent);
		}

	}


}
