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

public class CurrentTopicFragment extends Fragment {

	private ListView topicsList;
	private Context _ctx;
	private DBAdapter _dbaAdapter;
	public static int _catId;
	public static ArrayList<TopicBo> orgCurrentTopics;

	public CurrentTopicFragment(Context context, DBAdapter dbAdapter, int catId) {
		this._ctx = context;
		this._dbaAdapter = dbAdapter;
		//this._catId = catId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.current_topics, container, false);
		topicsList = (ListView) view.findViewById(R.id.lv_topics);

		TopicAdapter topicAdapter = new TopicAdapter(_ctx, orgCurrentTopics);

		topicsList.setAdapter(topicAdapter);
		return view;
		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		TopicAdapter topicAdapter = new TopicAdapter(_ctx, orgCurrentTopics);

		topicsList.setAdapter(topicAdapter);
	}

	/*
	 * private ArrayList<TopicBo> getCurrentTopics() { return
	 * TopicHelper.getCurrentTopicsbyCatId(_dbaAdapter, _catId); }
	 */
}
