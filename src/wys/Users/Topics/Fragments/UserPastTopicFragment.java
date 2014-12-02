package wys.Users.Topics.Fragments;

import java.util.ArrayList;

import com.wys.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import wys.Adapter.UserTopicsListAdapter;
import wys.Adapter.UserUpcomingTopicsListAdaptor;
import wys.Api.SessionManager;
import wys.AsyncTask.CommentAsyncTask;
import wys.AsyncTask.UserTopic;
import wys.Base.BaseFragment;
import wys.Business.CommentBo;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnGetCommentListener;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.TopicHelper;
import wys.Users.Comments.PastCommentsView;

public class UserPastTopicFragment extends BaseFragment implements
		OnGetTopicsListener, OnItemClickListener, OnGetCommentListener{

	private Context _ctx;
	private DBAdapter _dbAdapter;
	private ListView lv_UserPastTopics;
	private UserBo user;
	public static ArrayList<TopicBo> _usersPastTopics;
	public static int _catId;
	private TopicBo _selectedMyTopic;

	public UserPastTopicFragment(Context context, DBAdapter dbAdapter, int catid) {
		super(context);
		this._ctx = context;
		this._dbAdapter = dbAdapter;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// getUserPastTopics();

		View view = inflater.inflate(R.layout.past_topics, container, false);

		lv_UserPastTopics = (ListView) view.findViewById(R.id.lv_topics);
		lv_UserPastTopics.setOnItemClickListener(this);

		UserTopicsListAdapter adp = new UserTopicsListAdapter(_ctx,
				_usersPastTopics);
		lv_UserPastTopics.setAdapter(adp);
		return view;

		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	private void getUserPastTopics() {
		/*
		 * user = (UserBo) SessionManager.getUserBo(); return
		 * TopicHelper.getUserSpecificPastTopics(_dbAdapter, user.get_userId());
		 */
		new UserTopic(this, _ctx).executeGetClosedTopics(_catId);

	}

	@Override
	public void onTopicsReceived(ArrayList<TopicBo> list) {
		_usersPastTopics = list;
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
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		
		_selectedMyTopic = (TopicBo) parent.getItemAtPosition(position);

		CommentAsyncTask cmAsyncTask = new CommentAsyncTask(_ctx, _dbAdapter);
		cmAsyncTask.set_onCommentListener(this);
		cmAsyncTask.execteGetClosedComments(_selectedMyTopic.get_serverId());

	}

	@Override
	public void onCommentsReceived() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCommentNotReceived() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCLosedCommentsReceived(ArrayList<CommentBo> comments) {
		PastCommentsView.pastComments = comments;
		Intent i = new Intent(_ctx, PastCommentsView.class);
		i.putExtra("topic_name", _selectedMyTopic.get_name());
		i.putExtra("topic_id", _selectedMyTopic.get_serverId());
		i.putExtra("conclusion", _selectedMyTopic.get_conclusion());
		startActivity(i);
		
	}

}
