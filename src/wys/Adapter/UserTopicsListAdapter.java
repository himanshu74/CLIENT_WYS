package wys.Adapter;

import java.util.ArrayList;

import wys.Business.TopicBo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
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
			viewHolder.tv_status =(TextView) convertView.findViewById(R.id.tv_status);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolderUserTopics) convertView.getTag();
		}
         
		viewHolder.tv_topicName.setText(_topics_user.get(position).get_name());
         if(_topics_user.get(position).isSubScribedNotActive() == 0){
        	 viewHolder.tv_status.setVisibility(View.VISIBLE);
        	 viewHolder.tv_status.setText("Not Active");
         }else if (_topics_user.get(position).isSubScribedNotActive() == 1){
        	 viewHolder.tv_status.setVisibility(View.VISIBLE);
        	 viewHolder.tv_status.setText("Active");
        	 viewHolder.tv_status.setTextColor(getContext().getResources().getColor(R.color.myred));
         }
         else if (_topics_user.get(position).isSubScribedNotActive() == 2){
        	 viewHolder.tv_status.setVisibility(View.VISIBLE);
        	 viewHolder.tv_status.setText("Closed");
        	 viewHolder.tv_status.setTextColor(getContext().getResources().getColor(R.color.myred));
         }
		return convertView;
	}

	


}

class ViewHolderUserTopics {

	 TextView tv_topicName;
	 ImageView iv_forwardArrow;
	 TextView tv_status;
	

}

