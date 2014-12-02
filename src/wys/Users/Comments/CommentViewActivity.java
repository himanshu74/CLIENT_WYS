package wys.Users.Comments;

import java.util.ArrayList;

import com.wys.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import wys.AlarmService.WysAlarmActivity;
import wys.Api.SessionManager;
import wys.AsyncTask.CommentAsyncTask;
import wys.Background.CommentsDownloadService;
import wys.Base.BaseDbActivity;
import wys.BroadcastReceivers.WysBroadcastConstants;
import wys.Business.CommentBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnGetCommentListener;
import wys.CustomInterfaces.OnResponseReceived;

import wys.Helpers.FontHelper;
import wys.Helpers.PreferenceHelper;

public class CommentViewActivity extends BaseDbActivity implements
		OnClickListener, OnResponseReceived, OnGetCommentListener {

	private static int _topicId = -1;
	private String _topicName;
	private Context _ctx = CommentViewActivity.this;
	private ListView lv_user_comments;
	private TextView tv_topicName;
	private EditText et_new_comment;
	private ImageView iv_back, iv_send;
	private CommentHolderAdapter mHolder;
	private UserBo user;
	private CommentsNotiReceiver _comNotiReceiver;
	private ALarmCommentReceiver _alaCommentReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerReceiver();
		user = (UserBo) SessionManager.getUserBo();
		_topicName = getIntent().getStringExtra("topic_name");
		_topicId = getIntent().getIntExtra("topic_id", -1);
		setContentView(R.layout.users_comment);
		initControls();
		refereshData();

	}

	private void initControls() {
		lv_user_comments = (ListView) findViewById(R.id.lv_user_comments);
		tv_topicName = (TextView) findViewById(R.id.tv_topic_name);
		tv_topicName.setText(_topicName.toUpperCase());
		tv_topicName
				.setTypeface(GetTypeFace(FontHelper.CATEGORY_TITLE_FONTSTYLE));

		et_new_comment = (EditText) findViewById(R.id.et_new_comment);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_send = (ImageView) findViewById(R.id.iv_send);
		iv_back.setOnClickListener(this);
		iv_send.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == iv_back.getId()) {
			CommentViewActivity.this.finish();
		} else if (v.getId() == iv_send.getId()) {

			String comment = et_new_comment.getText().toString();
			if (comment.isEmpty()) {
				Toast.makeText(_ctx, "Please type something before posting",
						Toast.LENGTH_LONG).show();
			} else {
				et_new_comment.setText("");
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(et_new_comment.getWindowToken(), 0);
				CommentBo commentBo = new CommentBo();
				commentBo.set_topicId(_topicId);
				commentBo.set_userId(user.get_userId());
				commentBo.set_comment(comment);

				postComment(commentBo);
			}
		}
	}

	private void postComment(CommentBo commentbo) {
		CommentAsyncTask comAsyncTask = new CommentAsyncTask(_ctx, dbAdapter);
		comAsyncTask.set_onOnResponseReceived(this);
		comAsyncTask.executePostComment(commentbo);
	}

	private void startCommentDownloadService(int topic_id) {
		CommentsDownloadService.dbAdapter = dbAdapter;
		Intent i = new Intent(_ctx, CommentsDownloadService.class);
		i.putExtra("topic_id", topic_id);
		startService(i);
	}

	private void startAlarmManager(int topic_id) {
		Intent i = new Intent(_ctx, WysAlarmActivity.class);
		i.putExtra("topic_id", topic_id);
		startActivity(i);
	}

	@Override
	public void onResponseSuccess() {
		startCommentDownloadService(_topicId);
		/*
		 * CommentAsyncTask commentAsyncTask = new CommentAsyncTask(_ctx,
		 * dbAdapter); commentAsyncTask.set_onCommentListener(this);
		 * PreferenceHelper pref = getWYSPreferences(); int serverId =
		 * pref.get_commentsServerId(); if (serverId != -1) {
		 * commentAsyncTask.executeGetLatestComment(_topicId, serverId); } else
		 * { commentAsyncTask.executeGetCommentByTopic(_topicId); }
		 */
	}

	@Override
	public void onResponseFailure() {
		Toast.makeText(_ctx, "OOPS Something went wrong posting your comment",
				Toast.LENGTH_LONG).show();

	}

	@Override
	protected void onResume() {
		registerReceiver();
		registerAlarmReceiver();
		super.onResume();
	}

	@Override
	protected void onPause() {
		LocalBroadcastManager.getInstance(_ctx).unregisterReceiver(
				_comNotiReceiver);
		LocalBroadcastManager.getInstance(_ctx).unregisterReceiver(
				_alaCommentReceiver);
		super.onPause();

	}

	private void registerReceiver() {
		IntentFilter intentFilter = new IntentFilter(
				WysBroadcastConstants.COMMENTS_RECEIVED_ACTION);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		_comNotiReceiver = new CommentsNotiReceiver();
		_ctx.registerReceiver(_comNotiReceiver, intentFilter);

	}
	
	private void registerAlarmReceiver(){
		IntentFilter intentFilter = new IntentFilter(
				WysBroadcastConstants.ALARM_COMMENTS_RECEIVED);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		_alaCommentReceiver = new ALarmCommentReceiver();
		_ctx.registerReceiver(_alaCommentReceiver, intentFilter);
	}

	private void refereshData() {
		CommentListHelper commentListHelper = new CommentListHelper(dbAdapter);
		mHolder = commentListHelper.buildCommentHolderAdapter(_ctx,
				R.layout.users_comment_listview_row, _topicId);
		if (mHolder != null) {
			lv_user_comments.setAdapter(mHolder);
		}

	}

	private class CommentsNotiReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent intent) {
			if (intent.getIntExtra("status", -1) == 1) {
				Toast.makeText(_ctx,
						"Something went wrong while updating comments",
						Toast.LENGTH_LONG).show();
			} else {
				refereshData();
				//startAlarmManager(_topicId);
			}
		}

	}

	private class ALarmCommentReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getIntExtra("status", -1) == 1) {
				Toast.makeText(_ctx,
						"Something went wrong while updating comments",
						Toast.LENGTH_LONG).show();
			} else {
				refereshData();
				//startAlarmManager(_tohhhpicId);
			}
		}

	}

	@Override
	public void onCommentsReceived() {
		refereshData();

	}

	@Override
	public void onCommentNotReceived() {
		Toast.makeText(_ctx, "Cannot download the comments", Toast.LENGTH_LONG)
				.show();

	}

	@Override
	public void onCLosedCommentsReceived(ArrayList<CommentBo> comments) {
		// TODO Auto-generated method stub
		
	}

}
