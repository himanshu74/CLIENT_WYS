package wys.ForumObjects;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.wys.R;

import android.app.SearchManager.OnDismissListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import wys.Api.SessionManager;
import wys.AsyncTask.Topictask;
import wys.Base.BaseActivity;
import wys.Business.BaseBusiness;
import wys.Business.TopicBo;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.Dialogs.EmptyContentDialog;
import wys.Dialogs.NewTopicDialog;
import wys.FrontLayer.MainActivity;
import wys.Helpers.FontHelper;

public class CategoryActivity extends BaseActivity implements OnClickListener,
		android.content.DialogInterface.OnDismissListener, OnGetTopicsListener {

	private String _catName;
	private int _catId;
	private Context _ctx = CategoryActivity.this;
	public static boolean isNewTopicAddded;

	private TextView tv_item_heading;
	private ImageView iv_back, iv_logout, iv_deleteItem, iv_confirm, iv_cancel,
			btn_addnew;
	private ListView itemsListVIew;
	RelativeLayout rel_footer, rel_footer2;

	private ArrayList<TopicBo> topicList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topics);
		initControls();
		setListView();
	}

	@SuppressWarnings("unchecked")
	private void initControls() {
		Intent i = getIntent();
		_catName = i.getStringExtra("catName");
		_catId = i.getIntExtra("catId", -1);
		topicList = (ArrayList<TopicBo>) i.getSerializableExtra("list");
		tv_item_heading = (TextView) findViewById(R.id.tv_item_title);
		tv_item_heading.setText(_catName.toLowerCase());
		tv_item_heading
				.setTypeface(GetTypeFace(FontHelper.CATEGORY_TITLE_FONTSTYLE));
		itemsListVIew = (ListView) findViewById(R.id.lv_items);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		iv_logout = (ImageView) findViewById(R.id.iv_logout);
		iv_logout.setOnClickListener(this);
		iv_deleteItem = (ImageView) findViewById(R.id.iv_DeleteItem);
		btn_addnew = (ImageView) findViewById(R.id.btn_addnew_Item);
		btn_addnew.setOnClickListener(this);
		iv_confirm = (ImageView) findViewById(R.id.iv_delconfirm);
		iv_cancel = (ImageView) findViewById(R.id.iv_cancel);

		rel_footer = (RelativeLayout) findViewById(R.id.rel_footer);
		rel_footer2 = (RelativeLayout) findViewById(R.id.rel_footer2);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == btn_addnew.getId()) {
			NewTopicDialog newTopicDialog = new NewTopicDialog(_ctx, _catId);
			newTopicDialog.setCanceledOnTouchOutside(false);
			newTopicDialog.setOnDismissListener(this);
			newTopicDialog.show();
		}

		else if (v.getId() == iv_back.getId()) {
			onBackPressed();
		} else if (v.getId() == iv_logout.getId()) {
			SessionManager.setUserBo(null);
			Intent i = new Intent(_ctx, MainActivity.class);
			startActivity(i);
		}

	}

	@Override
	public void onBackPressed() {

		CategoryActivity.this.finish();

	}

	private void setListView() {

		if (topicList == null || topicList.size() <= 0) {
			ShowAutoDismissDialog();
		} else {
			CustomItemList customItemList = new CustomItemList(_ctx, topicList);
			itemsListVIew.setAdapter(customItemList);
		}

	}

	public void ShowAutoDismissDialog() {
		final EmptyContentDialog emptyContentDialog = new EmptyContentDialog(
				_ctx);
		emptyContentDialog.setCanceledOnTouchOutside(false);
		emptyContentDialog.show();
		final Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				emptyContentDialog.dismiss(); // when the task active then close
												// the dialog
				t.cancel(); // also just top the timer thread, otherwise, you
							// may receive a crash report
			}
		}, 5000);
	}

	private class CustomItemList extends ArrayAdapter<TopicBo> {

		private Context _ctx;
		private ArrayList<TopicBo> topicList;

		public CustomItemList(Context context, ArrayList<TopicBo> itemslist) {
			super(context, R.layout.topic_list);
			this._ctx = context;
			this.topicList = itemslist;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return topicList.size();
		}

		@Override
		public TopicBo getItem(int position) {
			// TODO Auto-generated method stub
			return topicList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return super.getItemId(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			LayoutInflater inflator = LayoutInflater.from(_ctx);

			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = inflator.inflate(R.layout.topic_list, null);
				viewHolder.tv_topicName = (TextView) convertView
						.findViewById(R.id.tv_itemName);
				viewHolder.iv_forwardArrow = (ImageView) convertView
						.findViewById(R.id.iv_arrow);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.tv_topicName.setText(topicList.get(position).get_name());
			viewHolder.tv_topicName
					.setTypeface(GetTypeFace(FontHelper.CATEGORY_NAME_FONTSTYLE));
			viewHolder.iv_forwardArrow.setBackgroundResource(R.drawable.arrow);
			return convertView;
		}

	}

	static class ViewHolder {

		TextView tv_topicName;
		ImageView iv_forwardArrow;
		CheckBox cb_DeleteCheckbx;

	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		if (isNewTopicAddded) {
			new Topictask(_ctx, CategoryActivity.this).executeGetTopics(_catId);
		}

	}

	@Override
	public void onTopicsReceived(ArrayList<BaseBusiness> list) {

		CustomItemList customItemList = new CustomItemList(_ctx,
				castTopicsList(list));
		itemsListVIew.setAdapter(customItemList);

	}

	private static ArrayList<TopicBo> castTopicsList(
			ArrayList<BaseBusiness> list) {

		ArrayList<TopicBo> topicList = new ArrayList<TopicBo>();
		for (BaseBusiness object : list) {
			TopicBo topicBo = (TopicBo) object;
			topicList.add(topicBo);

		}

		return topicList;
	}

	@Override
	public void onTopicsNotReceived() {
		Toast.makeText(_ctx, "OOPS!! Error Refereshing Data",
				Toast.LENGTH_SHORT).show();
		;

	}

}
