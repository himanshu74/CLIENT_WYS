package wys.Background;

import java.util.ArrayList;

import wys.Api.WysUserApi;
import wys.BroadcastReceivers.WysBroadcastConstants;
import wys.Business.CommentBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.CommentHelper;
import wys.Helpers.PreferenceHelper;
import wys.Modals.CommentModal;
import wys.Users.Comments.CommentListHelper;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CommentsDownloadService extends IntentService {

	public static DBAdapter dbAdapter;

	public CommentsDownloadService() {
		super("CommentsDownloadService");

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Context ctx =CommentsDownloadService.this;
		ArrayList<CommentBo> list;
		boolean status = false;
		int topic_id = intent.getIntExtra("topic_id", -1);
		PreferenceHelper prefs = new PreferenceHelper(ctx);

		if (!prefs.is_isCommentFirsttimeDown()) {
			int serverid = prefs.get_commentsServerId();
			if (serverid != -1) {
				WysUserApi wysUserApi = new WysUserApi();
				list = wysUserApi.getLatestComments(topic_id, serverid);
			} else {
				WysUserApi wysUserApi = new WysUserApi();
				list = wysUserApi.getcommentsBytopic(topic_id);
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
		} else {
			WysUserApi wysUserApi = new WysUserApi();
			list = wysUserApi.getcommentsBytopic(topic_id);
			if (list != null && list.size() > 0) {
				if (prefs.get_commentsServerId() != list.get(0).get_serverId()) {
					prefs.set_isCommentFirsttimeDown(false);
					prefs.set_commentsServerId(list.get(0).get_serverId());
					for (CommentBo comment : list) {
						status = CommentModal.saveComment(dbAdapter.getDb(),
								comment);
					}
				}

			}

		}
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
