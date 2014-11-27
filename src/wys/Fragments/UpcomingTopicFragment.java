package wys.Fragments;

import java.util.ArrayList;

import wys.Adapter.TopicAdapter;
import wys.Business.TopicBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.TopicHelper;

import com.wys.R;

import android.support.v4.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class UpcomingTopicFragment extends Fragment {

	private Context _ctx;
	private DBAdapter _dbadapter;
	private int _catId;
	private ListView topicsListView;

	public UpcomingTopicFragment(Context context, DBAdapter dbAdapter, int catId) {
		this._dbadapter = dbAdapter;
		this._ctx = context;
		this._catId = catId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.past_topics, container, false);
		topicsListView = (ListView) view.findViewById(R.id.lv_topics);

		TopicAdapter topicAdapter = new TopicAdapter(_ctx, getUpcomingTopics());

		topicsListView.setAdapter(topicAdapter);
		return view;
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	private ArrayList<TopicBo> getUpcomingTopics() {
		return TopicHelper.getUpComingTopicsByCatId(_dbadapter, _catId);
	}

	@Override
	public void onResume() {
		super.onResume();
		TopicAdapter topicAdapter = new TopicAdapter(_ctx, getUpcomingTopics());

		topicsListView.setAdapter(topicAdapter);
	}

}
