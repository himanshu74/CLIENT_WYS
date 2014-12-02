package wys.ORG;

import java.util.ArrayList;

import com.google.android.gms.internal.iv;
import com.wys.R;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import wys.AsyncTask.CommentAsyncTask;
import wys.AsyncTask.OrgAsync;
import wys.Base.BaseDbActivity;
import wys.Business.CommentBo;
import wys.Business.TopicBo;
import wys.CustomInterfaces.OnGetCommentListener;
import wys.CustomInterfaces.OnResponseReceived;

public class OrgPastCommentsView extends BaseDbActivity implements
		OnClickListener, OnResponseReceived, OnGetCommentListener {

	private ListView lv_comments;
	private TextView tv_topic_name, tv_conclusion_title, tv_conclusion;
	private EditText et_conclusion;
	private String _topicName;
	private int _topicId;
	private String _conclusion;
	private boolean isConclusionPosted=false;
	private Context _ctx = OrgPastCommentsView.this;
	public static ArrayList<CommentBo> OrgpastComments;
	private ImageView iv_post;
	public static String conclusion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.org_past_comments_view);
		_topicId = getIntent().getIntExtra("topic_id", -1);
		_topicName = getIntent().getStringExtra("topic_name");
		_conclusion = getIntent().getStringExtra("conclusion");
		initControls();
		setCommentAdp();
	}

	private void initControls() {
		tv_conclusion_title = (TextView) findViewById(R.id.tv_conclusion_title);
		if (_conclusion.isEmpty() || _conclusion.length() <= 0) {
			tv_conclusion_title.setText("Please post the conclusion");
		} else {
			tv_conclusion_title.setText("Conclusion");
		}
		lv_comments = (ListView) findViewById(R.id.lv_user_Past_comments);
		tv_topic_name = (TextView) findViewById(R.id.tv_topic_name);
		tv_conclusion=(TextView)findViewById(R.id.tv_conclusion);
		et_conclusion = (EditText) findViewById(R.id.et_conclusion);
		tv_topic_name.setText(_topicName);
		iv_post = (ImageView) findViewById(R.id.iv_post);
		iv_post.setOnClickListener(this);
	}

	
	private void shiftViews()
	{   iv_post.setVisibility(View.GONE);
		et_conclusion.setVisibility(View.GONE);
		tv_conclusion.setVisibility(View.VISIBLE);
		tv_conclusion.setText(conclusion);
	}
	private void setCommentAdp() {
		PastCommentsAdapter pastCommentsAdapter = new PastCommentsAdapter(_ctx);
		pastCommentsAdapter.addAll(OrgpastComments);
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
		if (v.getId() == iv_post.getId()) {
			conclusion = et_conclusion.getText().toString();
			if (conclusion.isEmpty() || conclusion.length() <= 0) {

				Toast.makeText(_ctx, "Please enter the conclusion",
						Toast.LENGTH_LONG).show();
             
			}else {
				postConclusion(conclusion);
			}
		}

	}
	
	private void postConclusion(String conclusion)
	{
		TopicBo topic = new TopicBo();
		topic.set_topicId(_topicId);
		topic.set_conclusion(conclusion);
		OrgAsync orgAsync = new OrgAsync(_ctx);
		orgAsync.set_onOnResponseReceived(this);
		orgAsync.executePostCOnclusion(topic);
	}

	@Override
	public void onResponseSuccess() {
		et_conclusion.setText("");shiftViews();
		CommentAsyncTask cmAsyncTask = new CommentAsyncTask(_ctx, dbAdapter);
		cmAsyncTask.set_onCommentListener(this);
		cmAsyncTask.execteGetClosedComments(_topicId);
	}

	@Override
	public void onResponseFailure() {
		// TODO Auto-generated method stub
		
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
		OrgPastCommentsView.OrgpastComments = comments;
		setCommentAdp();
	}

}
