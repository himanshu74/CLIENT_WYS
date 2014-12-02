package wys.Users.Topics.Fragments;

import java.util.ArrayList;

import com.wys.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import wys.AsyncTask.UserTopic;
import wys.Background.UserTopicGetService;
import wys.Base.BaseFragment;
import wys.BroadcastReceivers.WysBroadcastConstants;
import wys.Business.CategoryBo;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.CustomInterfaces.OnResponseReceived;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.TopicHelper;
import wys.Helpers.WysConstants;

public class UserUpcomingTopicFragment extends BaseFragment implements
		OnItemClickListener, OnResponseReceived,OnGetTopicsListener {

	private DBAdapter _dbAdapter;
	private Context _ctx;
	private ListView lv_userUpcomingtopics;
	public static int _catId;
	private TopicBo _topicCLicked;
	public static ArrayList<TopicBo> _UpcomingTopics;
	//private UserUpcomingTopicReceiver _topicReceiver;

	public UserUpcomingTopicFragment(Context context, DBAdapter dbAdapter,
			int catId) {
		super(context);
		this._ctx = context;
		this._dbAdapter = dbAdapter;


	}

	/*private void RegisterUserTopicReceiver() {
		IntentFilter intentFilter = new IntentFilter(
				WysBroadcastConstants.USER_GET_UPCOMING_TOPIC_INTENT_ACTION);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		_topicReceiver = new UserUpcomingTopicReceiver();
		_ctx.registerReceiver(_topicReceiver, intentFilter);
	}
*/
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	//	getUserUpcomingTopics();
		View view = inflater
				.inflate(R.layout.upcoming_topics, container, false);
		lv_userUpcomingtopics = (ListView) view.findViewById(R.id.lv_topics);
		lv_userUpcomingtopics.setOnItemClickListener(this);
        
		UserUpcomingTopicsListAdaptor adaptor = new UserUpcomingTopicsListAdaptor(
				_ctx, _UpcomingTopics);
		lv_userUpcomingtopics.setAdapter(adaptor);
		return view;
		// return super.onCreateView(inflater, container, savedInstanceState);
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

	private void refreshData() {
		
		new UserTopic(this,_ctx).executeGetUpcomingTopic(_catId);
		
	}

	@Override
	public void onResponseSuccess() {
		lv_userUpcomingtopics.setClickable(true);
		refreshData();
	}

	@Override
	public void onResponseFailure() {

	}

	@Override
	
    public void onTopicsReceived(ArrayList<TopicBo> list) {
		_UpcomingTopics = list;
		UserMyTopicFragment.IsDataChanged = true;
		UserUpcomingTopicsListAdaptor adaptor = new UserUpcomingTopicsListAdaptor(
				_ctx,_UpcomingTopics);
		lv_userUpcomingtopics.setAdapter(adaptor);
		
	}

	@Override
	public void onTopicsNotReceived() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEmptyServerRecord() {
		// TODO Auto-generated method stub
		
	}

	/*private class UserUpcomingTopicReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if ((intent.getIntExtra(UserTopicGetService.RESPONSE_TAG, -1) == WysConstants.RECEIVED_USER_TOPICS)) {
                    
			}

		}
	}*/

}
