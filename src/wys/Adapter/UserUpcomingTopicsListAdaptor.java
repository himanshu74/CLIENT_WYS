package wys.Adapter;

import java.util.ArrayList;

import com.wys.R;

import wys.Business.TopicBo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserUpcomingTopicsListAdaptor extends ArrayAdapter<TopicBo>  {

	
	private ArrayList<TopicBo> _topics_user;
	private Context _ctx;
	//public static boolean IsUpComingUserTopics;
	
	
	public UserUpcomingTopicsListAdaptor(Context context, ArrayList<TopicBo> topics) {
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

		ViewHolderUserUpcomingTopics viewHolder;
		LayoutInflater inflator = LayoutInflater.from(_ctx);

		if (convertView == null) {
			viewHolder = new ViewHolderUserUpcomingTopics();
			convertView = inflator.inflate(R.layout.users_upcoming_topic_list_item, null);
			viewHolder.tv_topicName = (TextView) convertView
					.findViewById(R.id.tv_itemName);
			viewHolder.tv_join = (TextView) convertView
					.findViewById(R.id.tv_join);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolderUserUpcomingTopics) convertView.getTag();
		}

		viewHolder.tv_topicName.setText(_topics_user.get(position).get_name());
         
		return convertView;
	}


}

class ViewHolderUserUpcomingTopics {

	 TextView tv_topicName;
	// ImageView iv_forwardArrow;
	 TextView tv_join;

}


