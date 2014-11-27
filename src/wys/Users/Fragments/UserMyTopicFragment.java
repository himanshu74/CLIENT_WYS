package wys.Users.Fragments;

import java.util.ArrayList;

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
import android.widget.ListView;
import wys.Adapter.UserTopicsListAdapter;
import wys.Adapter.UserUpcomingTopicsListAdaptor;
import wys.Api.SessionManager;
import wys.Base.BaseFragment;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.TopicHelper;
import wys.Helpers.WysConstants;

public class UserMyTopicFragment extends BaseFragment {

	private Context _ctx;
	private DBAdapter _dbAdapter;
	private ListView lv_UserCurrentTopics;
	private UserBo user;
	public static boolean IsDataChanged;
	MyReceiver r;

	public UserMyTopicFragment(Context context, DBAdapter dbAdapter) {
		super(context);
		this._ctx = context;
		this._dbAdapter = dbAdapter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.current_topics, container, false);
		lv_UserCurrentTopics = (ListView)view.findViewById(R.id.lv_topics);
		UserTopicsListAdapter adp = new UserTopicsListAdapter(_ctx,getUserCurrentTopics());
		lv_UserCurrentTopics.setAdapter(adp);
		return view;
		
		//return super.onCreateView(inflater, container, savedInstanceState);
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
	
	public void refereshData(){
		UserTopicsListAdapter adp = new UserTopicsListAdapter(_ctx,getUserCurrentTopics());
		lv_UserCurrentTopics.setAdapter(adp);	
	}
	
	
	private ArrayList<TopicBo> getUserCurrentTopics() {
		user = (UserBo) SessionManager.getUserBo();
		return TopicHelper.getUserSpecificCurrentTopics(_dbAdapter,
				user.get_userId());
	}

	private class MyReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			UserMyTopicFragment.IsDataChanged = false;
			UserMyTopicFragment.this.refereshData();
		}

	}
	
	
}
