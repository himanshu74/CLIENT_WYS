package wys.Adapter;

import wys.DatabaseHelpers.DBAdapter;
import wys.Fragments.CurrentTopicFragment;
import wys.Fragments.PastTopicFragment;
import wys.Fragments.UpcomingTopicFragment;
import wys.Users.Fragments.UserMyTopicFragment;
import wys.Users.Fragments.UserPastTopicFragment;
import wys.Users.Fragments.UserUpcomingTopicFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class UserTopicPagerAdapter extends FragmentPagerAdapter {

	private static final int TOPIC_FRAG_COUNT=3;
	private Context _ctx;
	private DBAdapter _dbAdapter;
	private int _catId;
	private UserMyTopicFragment _userCurrentTopicsFragment;
	private UserUpcomingTopicFragment _userUpcomingTopicsFragment;
	private UserPastTopicFragment _userPatTopicsTopicsFragments;
	
	
	
	
	public UserTopicPagerAdapter(FragmentManager fm, Context context, DBAdapter dbAdapter, int catId) {
		super(fm);
		_ctx = context;
		this._dbAdapter = dbAdapter;
		this._catId = catId;
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case 0: {

			return new UserPastTopicFragment(_ctx, _dbAdapter);

		}

		case 1: {

			return new UserMyTopicFragment(_ctx, _dbAdapter);
		}

		case 2:

		{
			return new UserUpcomingTopicFragment(_ctx, _dbAdapter,_catId);

		}

		}

		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return TOPIC_FRAG_COUNT;
	}

}
