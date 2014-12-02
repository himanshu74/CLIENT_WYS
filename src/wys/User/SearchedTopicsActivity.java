package wys.User;

import java.util.ArrayList;
import java.util.List;

import com.wys.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import wys.Adapter.UserTopicsListAdapter;
import wys.Api.SessionManager;
import wys.Base.BaseDbActivity;
import wys.Business.TopicBo;
import wys.Dialogs.SearchDialogActivity;
import wys.FrontLayer.MainActivity;

public class SearchedTopicsActivity extends BaseDbActivity implements OnClickListener {

	private ListView lv_search_topics;
	public static ArrayList<TopicBo> topics;
	private ImageView iv_back, iv_logout;
	private Context _ctx = SearchedTopicsActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.searched_topics);
		lv_search_topics =(ListView)findViewById(R.id.lv_topics);
		SearchedTopicAdapter adapter = new SearchedTopicAdapter(_ctx);
		adapter.addAll(topics);
		lv_search_topics.setAdapter(adapter);
		iv_back=(ImageView)findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		iv_logout =(ImageView)findViewById(R.id.iv_logout);
		iv_logout.setOnClickListener(this);
		
		
	}
	
	private class SearchedTopicAdapter extends ArrayAdapter<TopicBo> {
		private LayoutInflater mInflater;
		private Context _ctx;

		public SearchedTopicAdapter(Context context) {
			super(context, R.layout.user_topics_list_item);
			this._ctx = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			SearchTopicViewHolde viewHolder;

			if (convertView == null) {
				mInflater = LayoutInflater.from(_ctx);

				convertView = mInflater.inflate(R.layout.user_topics_list_item,
						parent, false);
				viewHolder = new SearchTopicViewHolde();
				viewHolder.tv_topicName = (TextView) convertView
						.findViewById(R.id.tv_itemName);
				viewHolder.tv_status = (TextView) convertView
						.findViewById(R.id.tv_status);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (SearchTopicViewHolde) convertView.getTag();
			}

			TopicBo topicBo = getItem(position);
			viewHolder.tv_topicName.setText(topicBo.get_name());
			if (topicBo.isSubScribedNotActive() == 0) {
				viewHolder.tv_status.setVisibility(View.VISIBLE);
				viewHolder.tv_status.setText("Not Active");
			} else if (topicBo.isSubScribedNotActive() == 1) {
				viewHolder.tv_status.setVisibility(View.VISIBLE);
				viewHolder.tv_status.setText("Active");
				viewHolder.tv_status.setTextColor(getContext().getResources()
						.getColor(R.color.myred));
			} else if (topicBo.isSubScribedNotActive() == 2) {
				viewHolder.tv_status.setVisibility(View.VISIBLE);
				viewHolder.tv_status.setText("Closed");
				viewHolder.tv_status.setTextColor(getContext().getResources()
						.getColor(R.color.myred));
			}
			return convertView;

		}

	}

	static class SearchTopicViewHolde {
		TextView tv_topicName;
		ImageView iv_forwardArrow;
		TextView tv_status;

	}

	@Override
	public void onClick(View v) {
		 if(v.getId() == iv_back.getId()){
			SearchedTopicsActivity.this.finish();
		}
		else if(v.getId() == iv_logout.getId()){
			Intent i = new Intent(SearchedTopicsActivity.this,MainActivity.class);
			SessionManager.setUserBo(null);
			startActivity(i);
			
		}
		
	}

}
