package wys.Adapter;

import java.util.ArrayList;

import wys.Business.BaseBusiness;
import wys.Business.TopicBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Fragments.CurrentTopicFragment;
import wys.Fragments.PastTopicFragment;
import wys.Fragments.UpcomingTopicFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;

public class TopicsPagerAdapter extends FragmentPagerAdapter {

	private Context _ctx;
	private DBAdapter _dbAdapter;
	private int _catId;
	private static final int TOPIC_FRAG_COUNT=3;

	public TopicsPagerAdapter(FragmentManager fm, Context context,
			DBAdapter dbAdapter, int catId) {
		super(fm);
		_ctx = context;
		this._dbAdapter = dbAdapter;
		this._catId = catId;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0: {

			return new PastTopicFragment(_ctx, _dbAdapter, _catId);

		}

		case 1: {

			return new CurrentTopicFragment(_ctx, _dbAdapter, _catId);
		}

		case 2:

		{
			return new UpcomingTopicFragment(_ctx, _dbAdapter, _catId);

		}

		}

		return null;
	}

	@Override
	public int getCount() {
		return TOPIC_FRAG_COUNT;
	}

}
