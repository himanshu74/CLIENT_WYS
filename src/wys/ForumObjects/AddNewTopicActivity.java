package wys.ForumObjects;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import com.wys.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import wys.AsyncTask.OrgAsync;
import wys.AsyncTask.Topictask;
import wys.Base.BaseActivity;
import wys.Base.BaseDbActivity;
import wys.Business.TopicBo;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.CustomInterfaces.OnTopicPostListener;
import wys.Dialogs.NewTopicDialog;
import wys.Fragments.UpcomingTopicFragment;

public class AddNewTopicActivity extends BaseDbActivity implements
		OnClickListener, OnTopicPostListener, OnGetTopicsListener {

	private Button btn_create;
	private EditText et_new_topic, et_tag;
	private Context _ctx = AddNewTopicActivity.this;
	private int domainid;
	private DatePicker dp;
	private String CategoryId = "CatId";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_topic);
		initControls();

	}

	private void initControls() {
		Intent i = getIntent();
		domainid = i.getIntExtra(CategoryId, -1);
		btn_create = (Button) findViewById(R.id.btn_create);
		btn_create.setOnClickListener(this);
		et_new_topic = (EditText) findViewById(R.id.et_new_topic);
		et_tag = (EditText) findViewById(R.id.et_tag);
		dp = (DatePicker) findViewById(R.id.dp);
		// dp.setMinDate(System.currentTimeMillis() - 1000);
	}

	@Override
	public void onClick(View v) {
		String topicName = et_new_topic.getText().toString();
		String keyword = et_tag.getText().toString();
		if (topicName.length() <= 0 || keyword.length() <= 0) {
			et_new_topic.setError("All fields are mandatory");
		} else {
			Topictask topictask = new Topictask(_ctx, dbAdapter);
			topictask.setOntopicPostListener(AddNewTopicActivity.this);
			getbeginDate();
			try {
				topictask.executePostTopics(topicName, keyword, domainid,
						getbeginDate());
			} catch (ParseException e) {

				e.printStackTrace();
			}
		}

	}

	private long getbeginDate() {

		long date = dp.getCalendarView().getDate();
		Date date1 = new Date(date);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
		String dateSTring = sdf.format(date1);

		return date;
	}

	@Override
	public void onTopicPosted() {
		downloadlatestData();
		Toast.makeText(_ctx, "New topic Posted", Toast.LENGTH_LONG).show();
		;

	}

	private void downloadlatestData() {
		new OrgAsync(_ctx, this).executeGetOrgUpTopics(domainid);
	}

	@Override
	public void onTopicPostError() {
		Toast.makeText(_ctx, "OOPS !! Something went wrong,Try again",
				Toast.LENGTH_LONG).show();
		;
	}

	@Override
	public void onTopicsReceived(ArrayList<TopicBo> list) {
		UpcomingTopicFragment.orgUpcomingList = list;
		CategoryActivity.isNewTopicAddded = true;
		AddNewTopicActivity.this.finish();

	}

	@Override
	public void onTopicsNotReceived() {

	}

	@Override
	public void onEmptyServerRecord() {
		// TODO Auto-generated method stub

	}

}
