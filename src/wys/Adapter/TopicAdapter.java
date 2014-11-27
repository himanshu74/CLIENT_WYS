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
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_topicName.setText(_topics.get(position).get_name());

		viewHolder.iv_forwardArrow.setBackgroundResource(R.drawable.arrow);
		return convertView;
	}
}

class ViewHolder {

	TextView tv_topicName;
	ImageView iv_forwardArrow;
	CheckBox cb_DeleteCheckbx;

}
