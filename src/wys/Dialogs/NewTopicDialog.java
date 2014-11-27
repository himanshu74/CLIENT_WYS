package wys.Dialogs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.wys.R;
import wys.AsyncTask.Topictask;
import wys.CustomInterfaces.OnTopicPostListener;
import wys.ForumObjects.CategoryActivity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class NewTopicDialog extends Dialog implements OnClickListener,
		OnTopicPostListener {

	private Button btn_create;
	private EditText et_new_topic;
	private Context _ctx;
	private int domainid;
	private DatePicker dp;

	public NewTopicDialog(Context context, int domainId) {
		super(context);
		this._ctx = context;
		this.domainid = domainId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.new_topic);
		initControls();
	}

	private void initControls() {

		btn_create = (Button) findViewById(R.id.btn_create);
		btn_create.setOnClickListener(this);
		et_new_topic = (EditText) findViewById(R.id.et_new_topic);
		dp = (DatePicker) findViewById(R.id.dp);
		dp.setMinDate(System.currentTimeMillis() - 1000);
	}

	@Override
	public void onClick(View v) {
	/*	String topicName = et_new_topic.getText().toString();
		if (topicName.length() <= 0) {
			et_new_topic.setError("topic Name cannot be empty");
		} else {
			Topictask topictask = new Topictask(_ctx);
			topictask.setOntopicPostListener(NewTopicDialog.this);
			try {
				topictask
						.executePostTopics(topicName, domainid, getbeginDate());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/

	}

	private String getbeginDate() {
		long date = dp.getCalendarView().getDate();
		Date d = new Date(date);
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		return format.format(d);
	}

	@Override
	public void onTopicPosted() {
		CategoryActivity.isNewTopicAddded = true;
		NewTopicDialog.this.dismiss();
		Toast.makeText(_ctx, "New topic Posted", Toast.LENGTH_LONG).show();
		;

	}

	@Override
	public void onTopicPostError() {
		Toast.makeText(_ctx, "OOPS !! Something went wrong,Try again",
				Toast.LENGTH_LONG).show();
		;

	}

}
