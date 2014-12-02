package wys.Dialogs;

import wys.AsyncTask.OrgAsync;
import wys.Business.TopicBo;
import wys.CustomInterfaces.OnResponseReceived;
import wys.Users.Comments.PastCommentsView;

import com.google.android.gms.internal.iv;
import com.wys.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ConclusionDialog extends Dialog implements
		android.view.View.OnClickListener, OnResponseReceived {

	private Context _ctx;
	private EditText et_conclusion;
	private ImageView iv_send;
	private int _topicId;
	String conclusion;

	public ConclusionDialog(Context context, int topicId) {
		super(context);

		this._ctx = context;
		this._topicId = topicId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post_conclusion_dialog);
		initConctrols();
	}

	private void initConctrols() {
		et_conclusion = (EditText) findViewById(R.id.Conclusion);
		iv_send = (ImageView) findViewById(R.id.iv_send);
		iv_send.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == iv_send.getId()) {
			conclusion = et_conclusion.getText().toString();
			if (conclusion.isEmpty() || conclusion.length() <= 0) {
				Toast.makeText(_ctx,
						"Please enter the conclusion before sending",
						Toast.LENGTH_LONG).show();

			}
			else {
				postConclusion(conclusion);
			}
		}
	}

	private void postConclusion(String conclusion) {
		TopicBo topic = new TopicBo();
		topic.set_topicId(_topicId);
		topic.set_conclusion(conclusion);
		OrgAsync orgAsync = new OrgAsync(_ctx);
		orgAsync.set_onOnResponseReceived(this);
		orgAsync.executePostCOnclusion(topic);
	}

	@Override
	public void onResponseSuccess() {
		PastCommentsView.conclusion=conclusion;
        ConclusionDialog.this.dismiss();
	}

	@Override
	public void onResponseFailure() {
		// TODO Auto-generated method stub

	}
}
