package wys.Users.Comments;

import com.wys.R;

import wys.Business.CommentBo;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CommentHolderAdapter extends ArrayAdapter<CommentBo> {

	private Context _ctx;
	private LayoutInflater mInflater;

	static class ViewHolder {
		TextView tv_username;
		TextView tv_comment;
	}

	public CommentHolderAdapter(Context context, int resource) {
		super(context, resource);
		_ctx = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.users_comment_listview_row, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.tv_username = (TextView) convertView
					.findViewById(R.id.tv_user_name);
			viewHolder.tv_comment = (TextView) convertView
					.findViewById(R.id.tv_user_Comment);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		CommentBo commentBo = getItem(position);
		viewHolder.tv_username.setText(commentBo.get_username());
		viewHolder.tv_comment.setText(commentBo.get_comment());

		return convertView;
	}

}
