package wys.Fragments;

import java.lang.reflect.Array;
import java.util.ArrayList;

import wys.Adapter.TopicAdapter;
import wys.Business.TopicBo;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.TopicHelper;

import com.wys.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.app.Fragment;

public class PastTopicFragment extends Fragment {

	private Context _ctx;
	private DBAdapter _dbadAdapter;
	private int _catId;

	private ListView topicsListView;

	public PastTopicFragment(Context context, DBAdapter dbAdapter, int catId) {
		this._dbadAdapter = dbAdapter;
		this._ctx = context;
		this._catId = catId;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.past_topics, container, false);
		topicsListView = (ListView) view.findViewById(R.id.lv_topics);

		TopicAdapter topicAdapter = new TopicAdapter(_ctx, getPastTopics());

		topicsListView.setAdapter(topicAdapter);
		return view;

		// return super.onCreateView(inflater, container, savedInstanceState);
	}

	private ArrayList<TopicBo> getPastTopics() {
		return TopicHelper.getPastTopicsByCatId(_dbadAdapter, _catId);
	}

	@Override
	public void onResume() {

		super.onResume();
		TopicAdapter topicAdapter = new TopicAdapter(_ctx, getPastTopics());

		topicsListView.setAdapter(topicAdapter);
	}

}
