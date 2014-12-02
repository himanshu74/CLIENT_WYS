package wys.Users.Topics.Fragments;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import com.wys.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import wys.Adapter.UserTopicsListAdapter;
import wys.Adapter.UserUpcomingTopicsListAdaptor;
import wys.Api.SessionManager;
import wys.AsyncTask.CommentAsyncTask;
import wys.AsyncTask.UserTopic;
import wys.Background.CommentsDownloadService;
import wys.Base.BaseFragment;
import wys.Business.CommentBo;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnGetCommentListener;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.PreferenceHelper;
import wys.Helpers.TopicHelper;
import wys.Helpers.WysConstants;
import wys.Users.Comments.CommentListHelper;
import wys.Users.Comments.CommentViewActivity;
import wys.Users.Comments.PastCommentsView;

public class UserMyTopicFragment extends BaseFragment implements
		OnGetTopicsListener, OnItemClickListener, OnGetCommentListener {

	private Context _ctx;
	private DBAdapter _dbAdapter;
	private ListView lv_UserCurrentTopics;
	private UserBo user;
	public static boolean IsDataChanged;
	MyReceiver r;
	public static ArrayList<TopicBo> _usersCurrentTopics;
	public static int _catId;
	private TopicBo _selectedMyTopic;

	public UserMyTopicFragment(Context context, DBAdapter dbAdapter, int catId) {
		super(context);
		this._ctx = context;
		this._dbAdapter = dbAdapter;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// getUserCurrentTopics();
		View view = inflater.inflate(R.layout.current_topics, container, false);
		lv_UserCurrentTopics = (ListView) view.findViewById(R.id.lv_topics);
		lv_UserCurrentTopics.setOnItemClickListener(this);
		UserTopicsListAdapter adp = new UserTopicsListAdapter(_ctx,
				_usersCurrentTopics);

		lv_UserCurrentTopics.setAdapter(adp);
		return view;

		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(_ctx).unregisterReceiver(r);

	}

	@Override
	public void onResume() {
		r = new MyReceiver();
		LocalBroadcastManager.getInstance(_ctx).registerReceiver(r,
				new IntentFilter(WysConstants.TAG_MY_TOPIC_REFERESH));

		super.onResume();
	}

	public void refereshData() {
		new UserTopic(this, _ctx).executeGetCurrentTopic(_catId);
	}

	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			UserMyTopicFragment.this.refereshData();
		}

	}

	@Override
	public void onTopicsReceived(ArrayList<TopicBo> list) {
		_usersCurrentTopics = list;
		UserMyTopicFragment.IsDataChanged = false;
		UserTopicsListAdapter adp = new UserTopicsListAdapter(_ctx,
				_usersCurrentTopics);
		lv_UserCurrentTopics.setAdapter(adp);
	}

	@Override
	public void onTopicsNotReceived() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEmptyServerRecord() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {

		_selectedMyTopic = (TopicBo) parent.getItemAtPosition(position);
		if (_selectedMyTopic.isSubScribedNotActive() == 1) {

			PreferenceHelper prefs = new PreferenceHelper(_ctx);
			if (prefs.is_isCommentFirsttimeDown()) {
				CommentAsyncTask cmAsyncTask = new CommentAsyncTask(_ctx,
						_dbAdapter);
				cmAsyncTask.set_onCommentListener(this);
				cmAsyncTask.executeGetCommentByTopic(_selectedMyTopic
						.get_serverId());
			} else {
				CommentAsyncTask cmAsyncTask = new CommentAsyncTask(_ctx,
						_dbAdapter);
				cmAsyncTask.set_onCommentListener(this);
				cmAsyncTask.executeGetLatestComment(
						_selectedMyTopic.get_serverId(),
						prefs.get_commentsServerId());
			}

		} else if (_selectedMyTopic.isSubScribedNotActive() == 2) {
			CommentAsyncTask cmAsyncTask = new CommentAsyncTask(_ctx,
					_dbAdapter);
			cmAsyncTask.set_onCommentListener(this);
			cmAsyncTask
					.execteGetClosedComments(_selectedMyTopic.get_serverId());
		}

		else {
			Toast.makeText(_ctx,
					"Sorry This topic is not Open for Commenting Yet",
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public void onCommentsReceived() {

		getWYSPreferences().set_isCommentFirsttimeDown(false);
		Intent i = new Intent(_ctx, CommentViewActivity.class);
		i.putExtra("topic_name", _selectedMyTopic.get_name());
		i.putExtra("topic_id", _selectedMyTopic.get_serverId());
		startActivity(i);
	}

	@Override
	public void onCommentNotReceived() {
		Toast.makeText(
				_ctx,
				"Seems No one Commented on this Topic, Be the first one go ahead",
				Toast.LENGTH_LONG).show();
		getWYSPreferences().set_isCommentFirsttimeDown(false);
		Intent i = new Intent(_ctx, CommentViewActivity.class);
		i.putExtra("topic_name", _selectedMyTopic.get_name());
		i.putExtra("topic_id", _selectedMyTopic.get_serverId());
		startActivity(i);

	}

	@Override
	public void onCLosedCommentsReceived(ArrayList<CommentBo> comments) {
	
	}

}
