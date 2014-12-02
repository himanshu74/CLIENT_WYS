package wys.ForumObjects;

import java.util.ArrayList;
import java.util.List;

import com.wys.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import wys.Api.SessionManager;
import wys.AsyncTask.OrgAsync;
import wys.AsyncTask.Topictask;
import wys.Background.OrgTopicGetService;
import wys.Background.UserTopicGetService;
import wys.Base.BaseDbActivity;
import wys.BroadcastReceivers.WysBroadcastConstants;
import wys.Business.BaseBusiness;
import wys.Business.CategoryBo;
import wys.Business.TopicBo;
import wys.Business.UserBo;
import wys.CustomInterfaces.OnGetTopicsListener;
import wys.Fragments.CurrentTopicFragment;
import wys.Fragments.PastTopicFragment;
import wys.Fragments.UpcomingTopicFragment;
import wys.FrontLayer.MainActivity;
import wys.Helpers.FontHelper;
import wys.Helpers.TopicHelper;
import wys.Helpers.WysConstants;

public class CategoryListActivity extends BaseDbActivity implements
		OnClickListener, OnItemClickListener, OnGetTopicsListener {

	private ListView categoryList;
	private ImageView iv_back, iv_DeleteCat, iv_logout, iv_confirm, iv_cancel;
	private TextView tv_Category_title;
	private ArrayList<CategoryBo> listCats;
	private Context _ctx = CategoryListActivity.this;
	private String catName;
	private int catId;
	private OrgTopicsReceiver _OrgTopicsReceiver;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		registerOrgReceiver();
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		listCats = (ArrayList<CategoryBo>) i.getSerializableExtra("list");
		setContentView(R.layout.category);
		initControls();
		setListview();

	}

	private void initControls() {
		categoryList = (ListView) findViewById(R.id.lv_categories);
		categoryList.setOnItemClickListener(this);
		tv_Category_title = (TextView) findViewById(R.id.tv_Category_title);
		tv_Category_title
				.setTypeface(GetTypeFace(FontHelper.CATEGORY_TITLE_FONTSTYLE));
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		iv_logout = (ImageView) findViewById(R.id.iv_logout);
		iv_logout.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == iv_back.getId()) {
			CategoryListActivity.this.finish();
		} else if (v.getId() == iv_logout.getId()) {
			SessionManager.setUserBo(null);
			Intent i = new Intent(_ctx, MainActivity.class);
			startActivity(i);
		}

	}

	private void registerOrgReceiver() {
		IntentFilter intentFilter = new IntentFilter(
				WysBroadcastConstants.ORG_GET_TOPIC_INTENT_ACTION);
		intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
		_OrgTopicsReceiver = new OrgTopicsReceiver();
		_ctx.registerReceiver(_OrgTopicsReceiver, intentFilter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		CategoryBo category = (CategoryBo) parent.getItemAtPosition(position);
		catName = category.get_categoryName();
		catId = category.get_serverId();

		UpcomingTopicFragment._catId = catId;
		CurrentTopicFragment._catId = catId;
		PastTopicFragment._catId = catId;
		Intent i = new Intent(_ctx, OrgTopicGetService.class);
		i.putExtra("catid", catId);
		_ctx.startService(i);
		

	}

	private void setListview() {
		CustomCategoryList customCategoryListAdapter = new CustomCategoryList(
				CategoryListActivity.this, listCats);
		categoryList.setAdapter(customCategoryListAdapter);
	}

	private class CustomCategoryList extends ArrayAdapter<CategoryBo> {

		private ArrayList<CategoryBo> categoryList;
		private Context context;

		public CustomCategoryList(Context context,
				ArrayList<CategoryBo> categories) {
			super(context, R.layout.category_list);
			this.context = context;
			this.categoryList = categories;

		}

		@Override
		public int getCount() {
			return listCats.size();
		}

		@Override
		public CategoryBo getItem(int position) {
			return listCats.get(position);
		}

		@Override
		public long getItemId(int position) {
			return super.getItemId(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			LayoutInflater inflator = LayoutInflater.from(context);
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = inflator.inflate(R.layout.category_list, null);
				viewHolder.tv_categoryName = (TextView) convertView
						.findViewById(R.id.tv_cat_name);
				viewHolder.iv_forwardArrow = (ImageView) convertView
						.findViewById(R.id.iv_arrow);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();

			}
			viewHolder.tv_categoryName.setText(categoryList.get(position)
					.get_categoryName());
			viewHolder.tv_categoryName
					.setTypeface(GetTypeFace(FontHelper.CATEGORY_NAME_FONTSTYLE));
			viewHolder.iv_forwardArrow.setBackgroundResource(R.drawable.arrow);
			return convertView;
			// return super.getView(position, convertView, parent);
		}

	}

	static class ViewHolder {

		ImageView iv_CategoryIcon;
		TextView tv_categoryName;
		ImageView iv_forwardArrow;
	}

	@Override
	public void onTopicsReceived(ArrayList<TopicBo> list) {
		UpcomingTopicFragment.orgUpcomingList = list;
		Intent i = new Intent(_ctx, CategoryActivity.class);
		i.putExtra("catName", catName);
		i.putExtra("catId", catId);
		startActivity(i);

	}

	@Override
	public void onTopicsNotReceived() {
		Toast.makeText(_ctx, "No Upcoming Topics for this Category",
				Toast.LENGTH_LONG).show();

	}

	@Override
	public void onEmptyServerRecord() {
		Toast.makeText(_ctx, "No Upcoming Topics for this Category",
				Toast.LENGTH_LONG).show();

	}

	private class OrgTopicsReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent intent) {
			if ((intent.getIntExtra(UserTopicGetService.RESPONSE_TAG, -1) == WysConstants.RECEIVED_USER_TOPICS)) {
				int catId = intent.getIntExtra("catid", -1);
				Intent i = new Intent(_ctx, CategoryActivity.class);
				i.putExtra("catid", catId);
				i.putExtra("catName", catName);
				startActivity(i);
			} else if ((intent
					.getIntExtra(UserTopicGetService.RESPONSE_TAG, -1) == WysConstants.NOT_RECEIVED_USER_TOPICS)) {
				Toast.makeText(_ctx, "Something went wrong please try again",
						Toast.LENGTH_SHORT).show();
			}

		}

	}

}
