package wys.Adapter;

import java.util.ArrayList;

import com.wys.R;

import wys.Business.TopicBo;

import wys.Helpers.FontHelper;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicAdapter extends ArrayAdapter<TopicBo> {

	private ArrayList<TopicBo> _topics;

	private Context _ctx;

	public TopicAdapter(Context context, ArrayList<TopicBo> topics) {
		super(context, R.layout.topic_list);

		_ctx = context;
		_topics = topics;

	}

	@Override
	public int getCount() {

		return _topics.size();
	}

	@Override
	public TopicBo getItem(int position) {
		// TODO Auto-generated method stub
		return _topics.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder viewHolder;
		LayoutInflater inflator = LayoutInflater.from(_ctx);

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflator.inflate(R.layout.topic_list, null);
			viewHolder.tv_topicName = (TextView) convertView
					.findViewById(R.id.tv_itemName);
			viewHolder.iv_forwardArrow = (ImageView) convertView
					.findViewById(R.id.iv_arrow);
			viewHolder.tv_status = (TextView) convertView
					.findViewById(R.id.tv_status);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_topicName.setText(_topics.get(position).get_name());
		
		if (_topics.get(position).get_isActive() == 0) {
			viewHolder.tv_status.setVisibility(View.VISIBLE);
			viewHolder.tv_status.setText("Not Active");
		} else if (_topics.get(position).get_isActive() == 1) {
			viewHolder.tv_status.setVisibility(View.VISIBLE);
			viewHolder.tv_status.setText("Active");
			viewHolder.tv_status.setTextColor(getContext().getResources()
					.getColor(R.color.myred));
		} else if (_topics.get(position).get_isActive() == 2) {
			viewHolder.tv_status.setVisibility(View.VISIBLE);
			viewHolder.tv_status.setText("Closed");
			viewHolder.tv_status.setTextColor(getContext().getResources()
					.getColor(R.color.myred));
		}
		
		return convertView;
	}
}

class ViewHolder {

	TextView tv_topicName;
	ImageView iv_forwardArrow;
	CheckBox cb_DeleteCheckbx;
	TextView tv_status;

}
