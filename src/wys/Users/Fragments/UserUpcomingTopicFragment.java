package wys.Users.Fragments;

import java.util.ArrayList;

import com.wys.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import wys.Adapter.UserUpcomingTopicsListAdaptor;
import wys.Api.SessionManager;
import wys.AsyncTask.UserAsynctask;
import wys.Base.BaseFragment;
import wys.Business.CategoryBo;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnResponseReceived;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.TopicHelper;

public class UserUpcomingTopicFragment extends BaseFragment implements
		OnItemClickListener, OnResponseReceived {

	private DBAdapter _dbAdapter;
	private Context _ctx;
	private ListView lv_userUpcomingtopics;
	private int _catId;
	private TopicBo _topicCLicked;

	public UserUpcomingTopicFragment(Context context, DBAdapter dbAdapter,
			int catId) {
		super(context);
		this._ctx = context;
		this._dbAdapter = dbAdapter;
		this._catId = catId;

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.upcoming_topics, container, false);
		lv_userUpcomingtopics = (ListView) view.findViewById(R.id.lv_topics);
		lv_userUpcomingtopics.setOnItemClickListener(this);

		UserUpcomingTopicsListAdaptor adaptor = new UserUpcomingTopicsListAdaptor(_ctx,
				getUserUpcomingTopics(_catId));
		lv_userUpcomingtopics.setAdapter(adaptor);
		return view;
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	private ArrayList<TopicBo> getUserUpcomingTopics(int catId) {
		return TopicHelper.getUserUpcomingTopicsByCatId(_dbAdapter, catId);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {
		_topicCLicked = (TopicBo) parent.getItemAtPosition(position);
		postUserTopic(_topicCLicked);

	}

	private void postUserTopic(TopicBo topic) {
		lv_userUpcomingtopics.setClickable(false);
		UserBo sessionedUser = (UserBo) SessionManager.getUserBo();
		UserBo user = new UserBo();
		ArrayList<TopicBo> topicList = new ArrayList<TopicBo>();
		topicList.add(topic);
		user.setUserTopics(topicList);
		user.set_userId(sessionedUser.get_userId());

		UserAsynctask userAsynctask = new UserAsynctask(_ctx, _dbAdapter);
		userAsynctask.set_onOnResponseReceived(this);
		userAsynctask.executePostJoinTopic(user);

	}

	private void refreshData(){
		UserUpcomingTopicsListAdaptor adaptor = new UserUpcomingTopicsListAdaptor(_ctx,
				getUserUpcomingTopics(_catId));
		lv_userUpcomingtopics.setAdapter(adaptor);
	}
	
	
	@Override
	public void onResponseSuccess() {
		lv_userUpcomingtopics.setClickable(true);
		UserMyTopicFragment.IsDataChanged = true;
		refreshData();
	}

	@Override
	public void onResponseFailure() {
           
	}

}
