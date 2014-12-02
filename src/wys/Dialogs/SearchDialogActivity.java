package wys.Dialogs;

import java.util.ArrayList;

import com.wys.R;

import wys.Api.SessionManager;
import wys.AsyncTask.Topictask;
import wys.Base.BaseDbActivity;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.DatabaseHelpers.DBAdapter;
import wys.FrontLayer.MainActivity;
import wys.User.SearchedTopicsActivity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class SearchDialogActivity extends Dialog implements OnClickListener,
		OnGetTopicsListener {

	public SearchDialogActivity(Context context, DBAdapter dbAdapter) {
		super(context);
		this._ctx = context;
		this._dbAdapter = dbAdapter;
	}

	private EditText et_search;
	private ImageView iv_search;
	private Context _ctx;
	private DBAdapter _dbAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.search_dialog);
		initControls();
	}

	private void initControls() {
		et_search = (EditText) findViewById(R.id.et_query);
		iv_search = (ImageView) findViewById(R.id.search_icon);
		iv_search.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == iv_search.getId()) {
			if (et_search.getText().toString().length() <= 0) {
				Toast.makeText(_ctx, "Please eneter a keyword",
						Toast.LENGTH_LONG).show();
			} else {
				getTopicsByKeyword(et_search.getText().toString());
			}
		}

	}

	private void getTopicsByKeyword(String key) {
		Topictask topictask = new Topictask(_ctx, SearchDialogActivity.this,
				_dbAdapter);
		topictask.executeGetAllTopicsByKeyword(key);

	}

	@Override
	public void onTopicsReceived(ArrayList<TopicBo> list) {
		SearchedTopicsActivity.topics = list;
		SearchDialogActivity.this.dismiss();
		Intent i = new Intent(_ctx, SearchedTopicsActivity.class);
		_ctx.startActivity(i);

	}

	@Override
	public void onTopicsNotReceived() {
		Toast.makeText(_ctx, "Sorry no results found", Toast.LENGTH_LONG)
				.show();

	}

	@Override
	public void onEmptyServerRecord() {
		// TODO Auto-generated method stub

	}

}
