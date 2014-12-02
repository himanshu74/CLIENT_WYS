package wys.Users.Comments;

import java.util.ArrayList;

import com.wys.R;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import wys.AsyncTask.CommentAsyncTask;
import wys.Base.BaseDbActivity;
import wys.Business.CommentBo;
import wys.Business.TopicBo;
import wys.CustomInterfaces.OnGetCommentListener;
import wys.Dialogs.ConclusionDialog;
import wys.Users.Comments.CommentHolderAdapter.ViewHolder;

public class PastCommentsView extends BaseDbActivity implements OnClickListener,OnDismissListener
{

	private ListView lv_comments;
	private TextView tv_conclusion, tv_topic_name;
	private ImageView btn_addnew_Item;
	private String _topicName;
	public static int _topicId;
	public static String conclusion;
	private Context _ctx = PastCommentsView.this;
	public static ArrayList<CommentBo> pastComments;
	public static boolean isOrganisatioCOntext = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.user_past_topic_comments);
		_topicId = getIntent().getIntExtra("topic_id", -1);
		_topicName = getIntent().getStringExtra("topic_name");
		conclusion = getIntent().getStringExtra("conclusion");
		initControls();
		setCOmmentsList();
		super.onCreate(savedInstanceState);
	}

	private void initControls() {
		tv_conclusion = (TextView) findViewById(R.id.tv_conclusion);
		tv_conclusion.setMovementMethod(new ScrollingMovementMethod());
		btn_addnew_Item = (ImageView) findViewById(R.id.btn_addnew_Item);
		if (isOrganisatioCOntext) {
			if (conclusion.isEmpty() || conclusion.length() <= 0)
				btn_addnew_Item.setVisibility(View.VISIBLE);
			btn_addnew_Item.setOnClickListener(this);
		}
		tv_conclusion.setText(conclusion);
		lv_comments = (ListView) findViewById(R.id.lv_user_Past_comments);
		tv_topic_name = (TextView) findViewById(R.id.tv_topic_name);
		tv_topic_name.setText(_topicName);

	}

	private void setCOmmentsList() {
		PastCommentsAdapter pastCommentsAdapter = new PastCommentsAdapter(_ctx);
		pastCommentsAdapter.addAll(pastComments);
		lv_comments.setAdapter(pastCommentsAdapter);
	}

	private class PastCommentsAdapter extends ArrayAdapter<CommentBo> {
		private LayoutInflater mInflater;
		private Context _ctx;

		public PastCommentsAdapter(Context context) {
			super(context, R.layout.users_comment_listview_row);
			this._ctx = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			PastViewHolder viewHolder;

			if (convertView == null) {
				mInflater = LayoutInflater.from(_ctx);

				convertView = mInflater.inflate(
						R.layout.users_comment_listview_row, parent, false);
				viewHolder = new PastViewHolder();
				viewHolder.tv_username = (TextView) convertView
						.findViewById(R.id.tv_user_name);
				viewHolder.tv_comment = (TextView) convertView
						.findViewById(R.id.tv_user_Comment);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (PastViewHolder) convertView.getTag();
			}

			CommentBo commentBo = getItem(position);
			viewHolder.tv_username.setText(commentBo.get_username());
			viewHolder.tv_comment.setText(commentBo.get_comment());

			return convertView;
		}

	}

	static class PastViewHolder {
		TextView tv_username;
		TextView tv_comment;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_addnew_Item.getId()) {
                   ConclusionDialog conclusionDialog = new ConclusionDialog(_ctx, _topicId);
                   conclusionDialog.setCanceledOnTouchOutside(false);
                   conclusionDialog.setOnDismissListener(this);
                   conclusionDialog.show();
		}

	}

	@Override
	public void onDismiss(DialogInterface dialog) {
	   tv_conclusion.setText(conclusion);
	}

	
	
}
