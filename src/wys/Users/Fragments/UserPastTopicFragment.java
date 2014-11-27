package wys.Users.Fragments;

import java.util.ArrayList;

import com.wys.R;

import android.content.Context;
import android.os.Bundle;
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

public class UserPastTopicFragment extends BaseFragment {

	private Context _ctx;
	private DBAdapter _dbAdapter;
	private ListView lv_UserPastTopics;
	private UserBo user;
	
	
	public UserPastTopicFragment(Context context, DBAdapter dbAdapter) {
		super(context);
		this._ctx = context;
		this._dbAdapter =dbAdapter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View view = inflater.inflate(R.layout.past_topics, container, false);
		
		lv_UserPastTopics = (ListView)view.findViewById(R.id.lv_topics);
		
		UserTopicsListAdapter adp = new UserTopicsListAdapter(_ctx,getUserPastTopics());
		lv_UserPastTopics.setAdapter(adp);
		return view;	
		
		//return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	private ArrayList<TopicBo> getUserPastTopics() {
		user = (UserBo) SessionManager.getUserBo();
		return TopicHelper.getUserSpecificPastTopics(_dbAdapter,
				user.get_userId());
	}
	
}
