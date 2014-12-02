package wys.Fragments;

import java.lang.reflect.Array;
import java.util.ArrayList;

import wys.Adapter.TopicAdapter;
import wys.AsyncTask.CommentAsyncTask;
import wys.Business.CommentBo;
import wys.Business.TopicBo;
import wys.CustomInterfaces.OnGetCommentListener;
import wys.DatabaseHelpers.DBAdapter;
import wys.Helpers.TopicHelper;
import wys.ORG.OrgPastCommentsView;
import wys.Users.Comments.PastCommentsView;

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
import android.support.v4.app.Fragment;

public class PastTopicFragment extends Fragment implements OnItemClickListener,
		OnGetCommentListener {

	private Context _ctx;
	private DBAdapter _dbadAdapter;
	public static int _catId;
	public static ArrayList<TopicBo> orgPastTopics;
	private TopicBo _selectedMyTopic;

	private ListView topicsListView;

	public PastTopicFragment(Context context, DBAdapter dbAdapter, int catId) {
		this._dbadAdapter = dbAdapter;
		this._ctx = context;
		// this._catId = catId;
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
		topicsListView.setOnItemClickListener(this);

		TopicAdapter topicAdapter = new TopicAdapter(_ctx, orgPastTopics);

		topicsListView.setAdapter(topicAdapter);
		return view;

	}

	@Override
	public void onResume() {

		super.onResume();
		refreshData();

	}

	private void refreshData() {
		TopicAdapter topicAdapter = new TopicAdapter(_ctx, orgPastTopics);
		topicsListView.setAdapter(topicAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position,
			long arg3) {

		_selectedMyTopic = (TopicBo) parent.getItemAtPosition(position);
		CommentAsyncTask cmAsyncTask = new CommentAsyncTask(_ctx, _dbadAdapter);
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
		/*
		 * OrgPastCommentsView.OrgpastComments = comments; Intent i = new
		 * Intent(_ctx, OrgPastCommentsView.class); i.putExtra("topic_name",
		 * _selectedMyTopic.get_name()); i.putExtra("topic_id",
		 * _selectedMyTopic.get_serverId()); i.putExtra("conclusion",
		 * _selectedMyTopic.get_conclusion()); startActivity(i);
		 */
		PastCommentsView.pastComments = comments;
		PastCommentsView.isOrganisatioCOntext=true;
		Intent i = new Intent(_ctx, PastCommentsView.class);
		i.putExtra("topic_name", _selectedMyTopic.get_name());
		i.putExtra("topic_id", _selectedMyTopic.get_serverId());
		i.putExtra("conclusion", _selectedMyTopic.get_conclusion());
		startActivity(i);
	}

}
