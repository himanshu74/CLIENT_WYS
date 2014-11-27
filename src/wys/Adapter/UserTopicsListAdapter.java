package wys.Adapter;

import java.util.ArrayList;

import wys.Business.TopicBo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wys.R;

public class UserTopicsListAdapter extends ArrayAdapter<TopicBo> {
	
	private ArrayList<TopicBo> _topics_user;
	private Context _ctx;
	
	
	public UserTopicsListAdapter(Context context, ArrayList<TopicBo> topics) {
		super(context, R.layout.user_topics_list_item);
		this._ctx = context;
		this._topics_user = topics;
		
	}
	
	
	@Override
	public int getCount() {

		return _topics_user.size();
	}

	@Override
	public TopicBo getItem(int position) {
		// TODO Auto-generated method stub
		return _topics_user.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolderUserTopics viewHolder;
		LayoutInflater inflator = LayoutInflater.from(_ctx);

		if (convertView == null) {
			viewHolder = new ViewHolderUserTopics();
			convertView = inflator.inflate(R.layout.user_topics_list_item, null);
			viewHolder.tv_topicName = (TextView) convertView
					.findViewById(R.id.tv_itemName);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolderUserTopics) convertView.getTag();
		}

		viewHolder.tv_topicName.setText(_topics_user.get(position).get_name());
         
		return convertView;
	}


}

class ViewHolderUserTopics {

	 TextView tv_topicName;
	 ImageView iv_forwardArrow;
	

}

